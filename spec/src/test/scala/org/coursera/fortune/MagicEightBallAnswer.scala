/*
 Copyright 2015 Coursera Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package org.coursera.fortune

import com.linkedin.data.schema.EnumDataSchema
import com.linkedin.data.template.DataTemplateUtil
import org.coursera.data.generated

/**
 * Magic eight ball answers.
 */
@generated(source="/org/coursera/fortune/MagicEightBallAnswer.pdsc")
object MagicEightBallAnswer extends Enumeration {
  type MagicEightBallAnswer = Value

  val IT_IS_CERTAIN = Value("IT_IS_CERTAIN")

  /**
   * Where later is at least 10 ms from now.
   */
  val ASK_AGAIN_LATER = Value("ASK_AGAIN_LATER")
  val OUTLOOK_NOT_SO_GOOD = Value("OUTLOOK_NOT_SO_GOOD")

  /**
   * Represents an unrecognized enumeration symbol.
   *
   * May be present when data writer is using a version of enumeration with symbols not yet
   * known to the data reader.
   */
  val $UNKNOWN = Value("$UNKNOWN")

  /**
   * Converts a string to an enumeration value. If the string does not match
   * any of the enumeration values, returns the $UKNOWN enumeration value.
   */
  def fromString(s: String): Value = {
    values.find(_.toString == s).getOrElse($UNKNOWN)
  }

  private val SCHEMA = DataTemplateUtil.parseSchema(
    """{
      |  "name": "MagicEightBallAnswer",
      |  "namespace": "org.coursera.fortune",
      |  "doc": "Magic eight ball answers.",
      |  "type": "enum",
      |  "symbols": [
      |    "IT_IS_CERTAIN",
      |    "ASK_AGAIN_LATER",
      |    "OUTLOOK_NOT_SO_GOOD"
      |  ],
      |  "symbolDocs": {
      |    "ASK_AGAIN_LATER": "Where later is at least 10 ms from now."
      |  }
      |}
    """.stripMargin).asInstanceOf[EnumDataSchema]
}
