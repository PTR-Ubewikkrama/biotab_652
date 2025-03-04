import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/domain/hh_device_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/hh_device_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/common_utils.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/common_popups.dart';
import 'package:pack_verity/views/common_components/qr_scan.dart';
import 'package:pack_verity/views/common_components/text_input.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:intl/intl.dart';

class HistoryPage extends StatefulWidget {
  const HistoryPage({super.key});

  @override
  State<HistoryPage> createState() => _HistoryPageState();
}

class _HistoryPageState extends State<HistoryPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController qrCodeController = TextEditingController();
  String error = '';
  final BTDeviceService _hhDeviceService = sl.get<BTDeviceService>();
  GetBTDeviceResponse? hhDeviceResponse;
  bool deleteClicked = false;

  void setScannedValue(String value, int index) {
    qrCodeController.text = value;
  }

  Future<void> _deleteDevice() async {
    if (_formKey.currentState?.validate() ?? false) {
      setState(() {
        deleteClicked = true;
      });

      try {
        final result = await _hhDeviceService
            .deleteBTDeviceByCode(hhDeviceResponse!.data!.deviceCode!);

        if (!mounted) return;

        if (result != null && result.isSuccess()) {
          handleSuccessC(context, "BT Device deleted successfully");
          setState(() {
            hhDeviceResponse = null;
            qrCodeController.text = '';
          });
        } else {
          handleError(context, "Something went wrong. Please try again");
        }
      } catch (_) {
        handleError(context, "Something went wrong. Please try again");
      } finally {
        setState(() {
          deleteClicked = false;
        });
      }
    }
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
            'BT Device Details',
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
                                  error = 'Please enter BT Device Code';
                                });
                                return 'Please enter BT Device Code';
                              } else {
                                return await getRemoveDevice(val).then((value) {
                                  if (value) {
                                    setState(() {
                                      error = '';
                                    });
                                    return null;
                                  } else {
                                    setState(() {
                                      error = 'BT Device not found';
                                    });
                                    return 'BT Device not found';
                                  }
                                });
                              }
                            },
                            labelText: 'QR Code BT Device ...',
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
                  hhDeviceResponse != null && hhDeviceResponse!.isSuccess()
                      ? Column(
                          children: [
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
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.deviceCode ??
                                            '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Power PCB")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.powerPcbCode ??
                                            '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.powerPcbCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Pump")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.pumpCode ??
                                            '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.pumpCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Fan")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.fanCode ?? '')),
                                    DataCell(getStatus(
                                        hhDeviceResponse!.data!.fanCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("UI PCB")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.uiPcbCode ??
                                            '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.uiPcbCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Encoder")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.encoderCode ??
                                            '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.encoderCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Main PCB")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.mainPcbCode ??
                                            '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.mainPcbCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Manifold")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.manifoldCode ??
                                            '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.manifoldCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(
                                        Text("Valve Card Inside Cable Set")),
                                    DataCell(Text(hhDeviceResponse!.data!
                                            .valveCardInsideCableSetCode ??
                                        '')),
                                    DataCell(getStatus(hhDeviceResponse!.data!
                                        .valveCardInsideCableSetCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text(
                                        "Valve Card Input Output Cable Set")),
                                    DataCell(Text(hhDeviceResponse!.data!
                                            .valveCardInputOutputCableSetCode ??
                                        '')),
                                    DataCell(getStatus(hhDeviceResponse!.data!
                                        .valveCardInputOutputCableSetCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Over Pressure Valve")),
                                    DataCell(Text(hhDeviceResponse!
                                            .data!.overPressureValveCode ??
                                        '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.overPressureValveCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Power Cable")),
                                    DataCell(Text(hhDeviceResponse!
                                            .data!.powerCableCode ??
                                        '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("UI Cable")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.uiCableCode ??
                                            '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Display")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.displayCode ??
                                            '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Front Bracket Assembly")),
                                    DataCell(Text(hhDeviceResponse!
                                            .data!.frontBracketAssemblyCode ??
                                        '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.frontBracketAssemblyCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Power Adaptor")),
                                    DataCell(Text(hhDeviceResponse!
                                            .data!.powerAdaptorCode ??
                                        '')),
                                    DataCell(getStatus(hhDeviceResponse!
                                        .data!.powerAdaptorCodeStatus)),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Enclosure Top")),
                                    DataCell(Text(hhDeviceResponse!
                                            .data!.enclosureTopCode ??
                                        '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Enclosure Bottom")),
                                    DataCell(Text(hhDeviceResponse!
                                            .data!.enclosureBottomCode ??
                                        '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Back Vent")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.backVentCode ??
                                            '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Fan Mount")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.fanMountCode ??
                                            '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Encoder Supporter")),
                                    DataCell(Text(hhDeviceResponse!
                                            .data!.encoderSupporterCode ??
                                        '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("PCB Holder")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.pcbHolderCode ??
                                            '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                for (int i = 0;
                                    i <
                                        (hhDeviceResponse!
                                                .data!.valveCards?.length ??
                                            0);
                                    i++)
                                  DataRow(
                                    cells: <DataCell>[
                                      DataCell(Text("Valve Card ${i + 1}")),
                                      DataCell(Text(hhDeviceResponse!
                                          .data!.valveCards![i])),
                                      DataCell(Text("")),
                                    ],
                                  ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Created By")),
                                    DataCell(Text(
                                        hhDeviceResponse!.data!.createdBy ??
                                            '')),
                                    DataCell(Text("")),
                                  ],
                                ),
                                DataRow(
                                  cells: <DataCell>[
                                    DataCell(Text("Created At")),
                                    DataCell(Text(formatDate(
                                        hhDeviceResponse!.data!.dateTime))),
                                    DataCell(Text("")),
                                  ],
                                ),
                              ],
                            ),
                            SizedBox(
                              height: 25,
                            ),
                            Row(
                              children: [
                                Expanded(
                                  child: SizedBox(
                                    height: 60,
                                    child: CupertinoButton(
                                      color: Colors.red,
                                      disabledColor: Colors.grey,
                                      onPressed: deleteClicked
                                          ? null
                                          : () {
                                              handleWarning(
                                                  context,
                                                  "Are you sure to delete device with code ${hhDeviceResponse!.data!.deviceCode}? This action cannot be undone.",
                                                  _deleteDevice);
                                            },
                                      child: deleteClicked
                                          ? const SpinKitWave(
                                              color: Colors.white,
                                              size: 20.0,
                                            )
                                          : const Text("Delete Device"),
                                    ),
                                  ),
                                ),
                              ],
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

  Future<bool> getRemoveDevice(String val) async {
    GetBTDeviceResponse? resp = await _hhDeviceService.getBTDevice(val);

    if (resp != null) {
      setState(() {
        hhDeviceResponse = resp;
      });
      if (!resp.isSuccess()) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }
}
