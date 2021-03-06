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

package org.coursera.data

import com.linkedin.data.DataList
import com.linkedin.data.DataMap
import com.linkedin.data.schema.DataSchema
import com.linkedin.data.schema.validation.RequiredMode
import com.linkedin.data.schema.validation.ValidateDataAgainstSchema
import com.linkedin.data.schema.validation.ValidationOptions
import com.linkedin.data.schema.validation.ValidationResult
import scala.collection.JavaConverters._

class DataValidationException(val validationResult: ValidationResult)
  extends RuntimeException(validationResult.getMessages.asScala.mkString(", "))

object DataTemplates {

  object DataConversion extends Enumeration {
    val SetReadOnly = Value("SetReadOnly")
    val DeepCopy = Value("DeepCopy")
  }
  type DataConversion = DataConversion.Value

  private[this] val validationOptions = new ValidationOptions(RequiredMode.FIXUP_ABSENT_WITH_DEFAULT)

  def makeImmutable(dataMap: DataMap, schema: DataSchema, conversion: DataConversion): DataMap = {
    val converted = conversion match {
      case DataConversion.DeepCopy => dataMap.copy()
      case DataConversion.SetReadOnly =>
        dataMap.setReadOnly()
        dataMap
    }

    val result = ValidateDataAgainstSchema.validate(converted, schema, validationOptions)
    if (result.isValid) {
      dataMap
    } else {
      throw new DataValidationException(result)
    }
  }

  def makeImmutable(dataList: DataList, schema: DataSchema, conversion: DataConversion): DataList = {
    val converted = conversion match {
      case DataConversion.DeepCopy => dataList.copy()
      case DataConversion.SetReadOnly =>
        dataList.setReadOnly()
        dataList
    }

    val result = ValidateDataAgainstSchema.validate(converted, schema, validationOptions)
    if (result.isValid) {
      dataList
    } else {
      throw new DataValidationException(result)
    }
  }

  def toUnionMap(data: Object, unionTag: String): DataMap = {
    val dataMap = new DataMap()
    dataMap.put(unionTag, data)
    dataMap.setReadOnly()
    dataMap
  }
}
