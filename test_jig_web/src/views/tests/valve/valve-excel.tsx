import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";

export function handleGenerateValveExcel(datas: any[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Idle Voltage Low Threshold",
      "Idle Voltage Up Threshold",
      "Idle Current Up Threshold",
      "Load Voltage Low Threshold",
      "Load Voltage Up Threshold",
      "Load Current Up Threshold",
      "Set Pressure",
      "Idle Voltage",
      "Idle Voltage Status",
      "Idle Current",
      "Idle Current Status",
      "Coil Resistance",
      "Operating Current",
      "Peak Power",
      "Average Power",
      "Flow Rate",
      "Flow Rate Status",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.idleVoltageLowThresh,
      data.idleVoltageUpThresh,
      data.idleCurrentUpThresh,
      data.loadVoltageLowThresh,
      data.loadVoltageUpThresh,
      data.loadCurrentUpThresh,
      data.setPressure,
      data.idleVoltage,
      data.idleVoltageStatus && data.idleVoltageStatus ? "Pass" : "Fail",
      data.idleCurrent,
      data.idleCurrentStatus && data.idleCurrentStatus ? "Pass" : "Fail",
      data.coilResistance,
      data.operatingCurrent,
      data.peakPower,
      data.averagePower,
      data.flowRate,
      data.flowRateStatus && data.flowRateStatus ? "Pass" : "Fail",
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Valve_Test_" + new Date().toISOString() + ".xlsx");
}
