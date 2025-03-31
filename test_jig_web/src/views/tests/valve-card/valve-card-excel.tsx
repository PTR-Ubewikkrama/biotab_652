import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { ValveCardTest } from "../../../services/valve_card_service";

export function handleGenerateValveCardExcel(datas: ValveCardTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Device Mac",
      "Location",
      "Physical Inspection State",
      "Rail",
      "Valve 1",
      "Valve 3",
      "Valve 5",
      "Valve 7",
      "Valve 2",
      "Valve 4",
      "Valve 6",
      "Valve 8",
      "Amperage Test",
      "Shift Register Test",
      "Overall Valve Card State",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.deviceMac,
      data.location,
      data.physicalInspectionState ? "Pass" : "Fail",
      data.rail,
      data.valve1,
      data.valve3,
      data.valve5,
      data.valve7,
      data.valve2,
      data.valve4,
      data.valve6,
      data.valve8,
      data.amperageTest,
      data.shiftRegisterTest,
      data.overallValveCardState,
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Valve_Card_Test_" + new Date().toISOString() + ".xlsx");
}
