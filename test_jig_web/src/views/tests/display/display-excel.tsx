import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { DisplayTest } from "../../../services/display_test_service";

export function handleGenerateDisplayExcel(datas: DisplayTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Physical Inspection State",
      "Back Light On",
      "Red Screen On",
      "Green Screen On",
      "Blue Screen On",
      "Color Patch",
      "BT Display Text",
      "Screen Off",
      "Overall Display Status",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.physicalInspectionState ? "Pass" : "Fail",
      data.backLightOn ? "Pass" : "Fail",
      data.redScreenOn ? "Pass" : "Fail",
      data.greenScreenOn ? "Pass" : "Fail",
      data.blueScreenOn ? "Pass" : "Fail",
      data.colorPatch ? "Pass" : "Fail",
      data.btdisplayText ? "Pass" : "Fail",
      data.screenOff ? "Pass" : "Fail",
      data.overallDisplayStatus ? "Pass" : "Fail",
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Display_Test_" + new Date().toISOString() + ".xlsx");
}
