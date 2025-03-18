import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { PowerPcbTest } from "../../../services/power_pcb_test_service";
import { format, parseISO } from "date-fns";

export function handleGeneratePcbTestExcel(datas: List<PowerPcbTest>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Test ID",
      "Serial Number",
      "Power Ground Resistance Upper Limit",
      "Power Ground Resistance",
      "Power Ground Resistance Status",
      "DC Barrel Jack Connectivity Status",
      "USB C Power Outlet Connectivity",
      "Status",
      "Date Time"
    ],
    ...datas.map((data) => [
      data.testId,
      data.serialNumber,
      data.powerGroundResistanceUpperLimit,
      data.powerGroundResistance,
      data.powerGroundResistanceStatus && data.powerGroundResistanceStatus ? "Pass" : "Fail",
      data.dcBarrelJackConnectivityStatus && data.dcBarrelJackConnectivityStatus ? "Pass" : "Fail",
      data.usbCPowerOutletConnectivity && data.usbCPowerOutletConnectivity ? "Pass" : "Fail",
      data.status,
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Power_PCB_Test_" + new Date().toISOString() + ".xlsx");
}
