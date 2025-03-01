import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";

export function handleGenerateValveExcel(datas: any[]) {

  const workbook = XLSX.utils.book_new();

  // Create a new worksheet 'NOISE_IDLE', 'SETTINGS_NOISE_MIN', 'SETTINGS_NOISE_MAX', 'NOISE_STATUS_IDLE', 'NOISE_FL', 'NOISE_STATUS_FL'
  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Device ID",
      "Qr Code",
      "Air Chamber Loading Pressure",
      "Air Chamber Status",
      "V1 Outlet Pressure After 10Ms On Time",
      "V1 Outlet On Status",
      "V1 Outlet Pressure After 10Ms Off Time",
      "V1 Outlet Off Status",
      "V2 Outlet Pressure After 10Ms On Time",
      "V2 Outlet On Status",
      "V2 Outlet Pressure After 10Ms Off Time",
      "V2 Outlet Off Status",
      "V3 Outlet Pressure After 10Ms On Time",
      "V3 Outlet On Status",
      "V3 Outlet Pressure After 10Ms Off Time",
      "V3 Outlet Off Status",
      "Valve Status",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.deviceId,
      data.qrCode,
      data.airChamberLoadingPressure,
      data.airChamberStatus ? "Pass" : "Fail",
      data.v1OutletPressureAfter10MsOnTime,
      data.v1OutletOnStatus ? "Pass" : "Fail",
      data.v1OutletPressureAfter10MsOffTime,
      data.v1OutletOffStatus ? "Pass" : "Fail",
      data.v2OutletPressureAfter10MsOnTime,
      data.v2OutletOnStatus ? "Pass" : "Fail",
      data.v2OutletPressureAfter10MsOffTime,
      data.v2OutletOffStatus ? "Pass" : "Fail",
      data.v3OutletPressureAfter10MsOnTime,
      data.v3OutletOnStatus ? "Pass" : "Fail",
      data.v3OutletPressureAfter10MsOffTime,
      data.v3OutletOffStatus ? "Pass" : "Fail",
      data.valveStatus ? "Pass" : "Fail",
      data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Valve_Test_" + new Date().toISOString() + ".xlsx");
}
