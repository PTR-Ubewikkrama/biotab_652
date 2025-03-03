import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { OPValveTest } from "../../../services/op_valve_service";

export function handleGenerateOPValveExcel(datas: OPValveTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Physical Inspection State",
      "Start Opening Pressure",
      "Start Opening Flowrate",
      "Valve Start Opening State",
      "Fully Opening Pressure",
      "Fully Opening Flowrate",
      "Valve Fully Opening State",
      "Closing Pressure",
      "Closing Flowrate",
      "Valve Closing State",
      "Overall Op Valve State",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.physicalInspectionState ? "Pass" : "Fail",
      data.startOpeningPressure,
      data.startOpeningFlowrate,
      data.valveStartOpeningState ? "Pass" : "Fail",
      data.fullyOpeningPressure,
      data.fullyOpeningFlowrate,
      data.valveFullyOpeningState ? "Pass" : "Fail",
      data.closingPressure,
      data.closingFlowrate,
      data.valveClosingState ? "Pass" : "Fail",
      data.overallOpValveState ? "Pass" : "Fail",
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "OP_Valve_Test_" + new Date().toISOString() + ".xlsx");
}
