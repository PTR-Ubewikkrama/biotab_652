import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { PowerPcbV2TestDto } from "../../../services/power_pcb_test_service";
import { format, parseISO } from "date-fns";

export function handleGeneratePcbV2TestExcel(datas: List<PowerPcbV2TestDto>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Test ID",
      "Serial Number",
      "Load Voltage Low Threshold",
      "Load Current Low Threshold",
      "USB C Power Outlet Connectivity",
      "Load Voltage",
      "Load Voltage Status",
      "Load Current Status",
      "Load Current",
      "Noise Level Status",
      "Status",
      "Date Time"
    ],
    ...datas.map((data) => [
      data.testId,
      data.serialNumber,
      data.loadVoltageLowThresh,
      data.loadCurrentLowThresh,
      data.usbCPowerOutletConnectivity && data.usbCPowerOutletConnectivity.toLowerCase() === "true"
        ? "Pass"
        : data.usbCPowerOutletConnectivity && data.usbCPowerOutletConnectivity.toLowerCase() != "false" ? "N/A" : "Fail",
      data.loadVoltage,
      data.loadVoltageStatus && data.loadVoltageStatus === true ? "Pass" : "Fail",
      data.loadCurrentStatus && data.loadCurrentStatus === true ? "Pass" : "Fail",
      data.loadCurrent,
      data.noiseLevelStatus && data.noiseLevelStatus === true ? "Pass" : "Fail",
      data.status && data.status === true ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Power_PCB_v2_Test_" + new Date().toISOString() + ".xlsx");
}
