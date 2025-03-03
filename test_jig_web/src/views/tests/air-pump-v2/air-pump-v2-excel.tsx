import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { AirPumpV2Test } from "../../../services/airPump_service";
import { format, parseISO } from "date-fns";

export function handleGenerateAirPumpV2Excel(datas: List<AirPumpV2Test>) {
  // Create a new workbook

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Flow Rate Low Threshold",
      "Flow Rate Up Threshold",
      "Load Voltage Low Threshold",
      "Load Voltage Up Threshold",
      "Load Current Up Threshold",
      "Pressure Low Threshold",
      "Pressure Up Threshold",
      "Pressure",
      "Pressure Status",
      "Load Voltage",
      "Load Voltage Status",
      "Load Current",
      "Load Current Status",
      "Flow Rate",
      "Flow Rate Status",
      "Noise Level Status",
      "Status",
      "Date Time"
    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.flowRateLowThresh,
      data.flowRateUpThresh,
      data.loadVoltageLowThresh,
      data.loadVoltageUpThresh,
      data.loadCurrentUpThresh,
      data.pressureLowThresh,
      data.pressureUpThresh,
      data.pressure,
      data.pressureStatus && data.pressureStatus ? "Pass" : "Fail",
      data.loadVoltage,
      data.loadVoltageStatus && data.loadVoltageStatus ? "Pass" : "Fail",
      data.loadCurrent,
      data.loadCurrentStatus && data.loadCurrentStatus ? "Pass" : "Fail",
      data.flowRate,
      data.flowRateStatus && data.flowRateStatus ? "Pass" : "Fail",
      data.noiseLevelStatus && data.noiseLevelStatus ? "Pass" : "Fail",
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Air_Pump_V2_Test_" + new Date().toISOString() + ".xlsx");
}
