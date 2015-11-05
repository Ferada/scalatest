/*
* Copyright 2001-2015 Artima, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import sbt._
import sbt.Keys._

trait EasyMockModules {

  def sharedSettings: Seq[Setting[_]]

  def scalatestCore: Project

  def scalatestTestOptions: Seq[Tests.Argument]

  def scalatestLibraryDependencies: Seq[ModuleID]

  def commonTest: Project

  lazy val scalatestEasyMock = Project("scalatestEasyMock", file("scalatest-easymock"))
    .settings(sharedSettings: _*)
    .settings(
      organization := "org.scalatest",
      moduleName := "scalatest-easymock",
      libraryDependencies += "org.easymock" % "easymockclassextension" % "3.1" % "optional"
    ).dependsOn(scalatestCore).aggregate(LocalProject("scalatestEasyMockTest"))

  lazy val scalatestEasyMockTest = Project("scalatestEasyMockTest", file("scalatest-easymock-test"))
    .settings(sharedSettings: _*)
    .settings(
      testOptions in Test := scalatestTestOptions,
      libraryDependencies ++= scalatestLibraryDependencies,
      libraryDependencies += "org.easymock" % "easymockclassextension" % "3.1" % "test",
      publishArtifact := false,
      publish := {},
      publishLocal := {}
    ).dependsOn(scalatestEasyMock % "test", commonTest % "test")

}