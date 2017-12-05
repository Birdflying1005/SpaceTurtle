/*
 * Copyright 2017 Max Meldrum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import sbt._

object Dependencies {
  val scalaTestVersion = "3.0.4"
  val curatorVersion = "4.0.0"
  val scalaLoggingVersion = "3.5.0"
  val logbackVersion = "1.2.3"
  val typeConfigVersion = "1.3.1"
  val akkaHttpVersion = "10.0.10"
  val akkaHttpCirceVersion = "1.18.0"
  val circeVersion = "0.8.0"
  val http4sVersion = "0.17.5"


  val logDependencies: Seq[ModuleID] = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion
  )

  val confDependencies: Seq[ModuleID] = Seq(
    "com.typesafe" % "config" % typeConfigVersion
  )

  val testDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  )

  val curatorDependencies: Seq[ModuleID] = Seq(
    "org.apache.curator" % "curator-framework",
    "org.apache.curator" % "curator-test",
    "org.apache.curator" % "curator-recipes"
  ).map(_ % curatorVersion)

  val circeDependencies: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-jawn",
    "io.circe" %% "circe-parser"
  ).map(_ % circeVersion)

  val http4sDependencies: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-dsl",
    "org.http4s" %% "http4s-blaze-server",
    "org.http4s" %% "http4s-circe"
  ).map(_ % http4sVersion)

  val akkaHttpDependencies: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirceVersion
  )

  // Common libs that are used together
  val common : Seq[ModuleID] =
    logDependencies ++ confDependencies ++ testDependencies ++ curatorDependencies

  val zookeeperDependencies: Seq[ModuleID] = common ++ circeDependencies
  val agentDependencies : Seq[ModuleID] = common ++ http4sDependencies
  val masterDependencies : Seq[ModuleID] = common ++ akkaHttpDependencies ++ circeDependencies
  val cliDependencies : Seq[ModuleID] = agentDependencies
}