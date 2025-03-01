import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/domain/cartoon_package_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/fa_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/common_utils.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/qr_scan.dart';
import 'package:pack_verity/views/common_components/text_input.dart';
import 'package:intl/intl.dart';

class UDIScanPage extends StatefulWidget {
  const UDIScanPage({super.key});

  @override
  State<UDIScanPage> createState() => _UDIScanPageState();
}

class _UDIScanPageState extends State<UDIScanPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController qrCodeController = TextEditingController();
  String error = '';
  final FAService faService = sl.get<FAService>();
  GetCartoonPackageResponse? getCPFullResp;
  bool deleteClicked = false;

  void setScannedValue(String value, int index) {
    qrCodeController.text = value;
  }

  String formatDate(String? date) {
    if (date != null) {
      DateTime dateTime = DateTime.parse(date);

      return DateFormat('yyyy-MM-dd HH:mm:ss').format(dateTime);
    } else {
      return '';
    }
  }

  Widget getStatus(String? status) {
    if (status != null) {
      if (status == 'VERIFIED') {
        return const Text('VERIFIED',
            style: TextStyle(color: Colors.green, fontWeight: FontWeight.bold));
      } else {
        return const Text('NOT VERIFIED',
            style: TextStyle(color: Colors.red, fontWeight: FontWeight.bold));
      }
    } else {
      return const Text('NOT ADDED',
          style: TextStyle(color: Colors.orange, fontWeight: FontWeight.bold));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          elevation: 0.0,
          systemOverlayStyle: const SystemUiOverlayStyle(
            statusBarColor: colorInputField,
            statusBarIconBrightness: Brightness.light,
            statusBarBrightness: Brightness.light,
          ),
          backgroundColor: colorInputField,
          centerTitle: true,
          iconTheme: const IconThemeData(
            color: Colors.white,
          ),
          title: Text(
            'UDI Scan',
            style: const TextStyle(
                color: Colors.white, fontWeight: FontWeight.bold),
          ),
        ),
        body: SingleChildScrollView(
          child: Center(
              child: Form(
            key: _formKey,
            child: Padding(
              padding: const EdgeInsets.all(15.0),
              child: Column(
                children: [
                  SizedBox(
                    height: 20,
                  ),
                  Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Expanded(
                        flex: 5,
                        child: TextInputCustom(
                          data: TestInputData(
                            controller: qrCodeController,
                            validatorFun: null,
                            asyncValidatorFun: (val) async {
                              if (val!.isEmpty) {
                                setState(() {
                                  error = 'Please enter UDI Code';
                                });
                                return 'Please enter UDI Code';
                              } else {
                                return await faService
                                    .getCartoonPackageByUDINumber(val)
                                    .then((value) {
                                  if (value != null && value.isSuccess()) {
                                    setState(() {
                                      error = '';
                                      getCPFullResp = value;
                                    });
                                    return null;
                                  } else {
                                    setState(() {
                                      error = 'UDI Code not found';
                                    });
                                    return 'UDI Code not found';
                                  }
                                });
                              }
                            },
                            labelText: 'UDI Code ...',
                          ),
                        ),
                      ),
                      FutureBuilder(
                          future: getZoomVal(cameraZoomLevel,
                              cameraScanAreaScale, cameraFlashStatus),
                          builder: (context, snapshot) {
                            if (snapshot.hasData) {
                              return Expanded(
                                flex: 1,
                                child: Padding(
                                  padding:
                                      const EdgeInsets.only(left: 8.0, top: 0),
                                  child: SizedBox(
                                    height: 55,
                                    child: CupertinoButton(
                                      padding: const EdgeInsets.all(0),
                                      color: Colors.black,
                                      child: const Icon(
                                        Icons.qr_code,
                                        color: Colors.white,
                                      ),
                                      onPressed: () {
                                        Navigator.push(
                                            context,
                                            MaterialPageRoute(
                                                builder: (context) =>
                                                    QRScanView(setScannedValue,
                                                        areaScale: snapshot
                                                            .data!.scaleVal,
                                                        flashStatus: snapshot
                                                            .data!.flashVal,
                                                        zoomScale: snapshot
                                                            .data!.zoomVal)));
                                      },
                                    ),
                                  ),
                                ),
                              );
                            } else {
                              return Expanded(flex: 1, child: Container());
                            }
                          }),
                    ],
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  getCPFullResp != null && getCPFullResp!.isSuccess()
                      ? Column(
                          children: [
                            Text("Final Assembly Information",
                                style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold)),
                            SizedBox(
                              height: 10,
                            ),
                            DataTable(
                              columnSpacing: 16.0,
                              columns: const <DataColumn>[
                                DataColumn(
                                  headingRowAlignment: MainAxisAlignment.center,
                                  label: Expanded(
                                    child: Text(
                                      'Code Key',
                                      style: TextStyle(
                                          fontStyle: FontStyle.italic),
                                    ),
                                  ),
                                ),
                                DataColumn(
                                  label: Expanded(
                                    child: Text(
                                      'Value',
                                      style: TextStyle(
                                          fontStyle: FontStyle.italic),
                                    ),
                                  ),
                                ),
                              ],
                              rows: <DataRow>[
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Device Code")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.finalAssembly!.deviceCode ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Category")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.finalAssembly!.category ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Bladder Code")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.finalAssembly!.bladderCode ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("UPL Number")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.finalAssembly!.uplNumber ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("UDI Number")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.finalAssembly!.udiNumber ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Adapter Code")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.finalAssembly!.adapterCode ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Cartoon Number")),
                                    DataCell(Text(getCPFullResp!.data!
                                            .finalAssembly!.cartoonNumber ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Created By")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.finalAssembly!.createdBy ??
                                        '')),
                                  ],
                                ),
                              ],
                            ),
                            SizedBox(
                              height: 20,
                            ),
                            Text("Cartoon Information",
                                style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold)),
                            SizedBox(
                              height: 10,
                            ),
                            DataTable(
                              columnSpacing: 16.0,
                              columns: const <DataColumn>[
                                DataColumn(
                                  headingRowAlignment: MainAxisAlignment.center,
                                  label: Expanded(
                                    child: Text(
                                      'Code Key',
                                      style: TextStyle(
                                          fontStyle: FontStyle.italic),
                                    ),
                                  ),
                                ),
                                DataColumn(
                                  label: Expanded(
                                    child: Text(
                                      'Value',
                                      style: TextStyle(
                                          fontStyle: FontStyle.italic),
                                    ),
                                  ),
                                ),
                              ],
                              rows: <DataRow>[
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Cartoon Number")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.cartoonBox!.cartoonNumber ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Created By")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.cartoonBox!.createdBy ??
                                        '')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Date Time")),
                                    DataCell(Text(formatDate(getCPFullResp!
                                            .data!.cartoonBox!.createdAt) ??
                                        '')),
                                  ],
                                ),
                              ],
                            ),
                            SizedBox(
                              height: 20,
                            ),
                            Text("Device Infromation",
                                style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold)),
                            SizedBox(
                              height: 10,
                            ),
                            DataTable(
                              columnSpacing: 16.0,
                              columns: const <DataColumn>[
                                DataColumn(
                                  headingRowAlignment: MainAxisAlignment.center,
                                  label: Expanded(
                                    child: Text(
                                      'Code Key',
                                      style: TextStyle(
                                          fontStyle: FontStyle.italic),
                                    ),
                                  ),
                                ),
                                DataColumn(
                                  label: Expanded(
                                    child: Text(
                                      'Value',
                                      style: TextStyle(
                                          fontStyle: FontStyle.italic),
                                    ),
                                  ),
                                ),
                                DataColumn(
                                  label: Expanded(
                                    child: Text(
                                      'Status',
                                      style: TextStyle(
                                          fontStyle: FontStyle.italic),
                                    ),
                                  ),
                                ),
                              ],
                              rows: <DataRow>[
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Device")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.deviceCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.deviceCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("PCB")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.pcbTestCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.pcbTestCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Valve One")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.valveTestOneCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.valveTestOneCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Valve Two")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.valveTestTwoCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.valveTestTwoCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Air Pump")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.airPumpTestCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.airPumpTestCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Power Button")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.latchButtonTestCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!.data!
                                        .device.latchButtonTestCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Over Pressure Valve")),
                                    DataCell(Text(getCPFullResp!.data!.device
                                            .overPressureValveTestCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!
                                        .device
                                        .overPressureValveTestCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Battery Test")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.batteryTestCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.batteryTestCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Enclosure")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.enclosureCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.enclosureCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Air Bladder")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.airBladderCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!
                                        .data!.device.airBladderCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Power Supply")),
                                    DataCell(Text(getCPFullResp!
                                            .data!.device.powerSupplyTestCode ??
                                        '')),
                                    DataCell(getStatus(getCPFullResp!.data!
                                        .device.powerSupplyTestCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Created By")),
                                    DataCell(Text(
                                        getCPFullResp!.data!.device.createdBy ??
                                            '')),
                                    DataCell(Text('')),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Date Time")),
                                    DataCell(Text(formatDate(getCPFullResp!
                                            .data!.device.dateTime) ??
                                        '')),
                                    DataCell(Text('')),
                                  ],
                                ),
                              ],
                            ),
                            SizedBox(
                              height: 25,
                            ),
                          ],
                        )
                      : Container(),
                ],
              ),
            ),
          )),
        ));
  }
}
