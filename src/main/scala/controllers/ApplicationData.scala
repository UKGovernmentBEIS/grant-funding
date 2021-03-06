/*
 * Copyright (C) 2016  Department for Business, Energy and Industrial Strategy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package controllers

import forms._
import forms.validation._
import models.{AppSectionNumber, BusinessKey, ProcessDefinition, ProcessDefinitionId, ProcessVariable}
import play.api.libs.json._

object ApplicationData {

  sealed trait SectionType

  case object ItemSection extends SectionType

  case object VanillaSection extends SectionType

  import FieldChecks._

  implicit val civReads = Json.reads[CostItemValues]
  implicit val ciReads = Json.reads[CostItem]
  implicit val cifReads = Json.reads[FileUploadItem]
  implicit val processDefinitionFmt1 = Json.format[ProcessDefinitionId]
  implicit val processDefinitionFmt2 = Json.format[BusinessKey]
  implicit val processDefinitionFmt3 = Json.format[ProcessVariable]
  implicit val processDefinitionFmt = Json.format[ProcessDefinition]


  def itemChecksFor(sectionNumber: AppSectionNumber): Map[String, FieldCheck] = sectionNumber.num.value match {
    case
      6 => Map("item" -> fromValidator(CostItemValidator))
    case _ => Map()
  }

  def itemFieldsFor(sectionNum: AppSectionNumber): Option[Seq[Field]] = sectionNum.num.value match {
    case 6 => Some(Seq(CostItemField("item")))
    case _ => None
  }
}
