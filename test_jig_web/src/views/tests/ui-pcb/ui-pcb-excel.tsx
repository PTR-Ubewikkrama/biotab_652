import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { UIPcbTest } from "../../../services/ui_pcb_service";

export function handleGenerateManiFoldLeakExcel(datas: UIPcbTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Physical Inspection State",
      "Red Led State",
      "White Led State",
      "Led Ring On State",
      "Led Ring Fade State",
      "Overall UiPcb State",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.physicalInspectionState ? "Pass" : "Fail",
      data.redLedState ? "Pass" : "Fail",
      data.whiteLedState ? "Pass" : "Fail",
      data.ledRingOnState ? "Pass" : "Fail",
      data.ledRingFadeState ? "Pass" : "Fail",
      data.overallUiPcbState ? "Pass" : "Fail",
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "UI-Pcb_Test_" + new Date().toISOString() + ".xlsx");
}
