import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/domain/component_verification_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/hh_device_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/common_utils.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/qr_scan.dart';
import 'package:pack_verity/views/common_components/text_input.dart';

class VerifyComponentPage extends StatefulWidget {
  const VerifyComponentPage({super.key});

  @override
  State<VerifyComponentPage> createState() => _VerifyComponentPageState();
}

class _VerifyComponentPageState extends State<VerifyComponentPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController qrCodeController = TextEditingController();
  String error = '';
  final HhDeviceService _hhDeviceService = sl.get<HhDeviceService>();
  ComponentVerificationResp? hhDeviceResponse;

  void setScannedValue(String value, int index) {
    qrCodeController.text = value;
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
            'Verify Component',
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
                                  error = 'Please enter QR Code';
                                });
                                return 'Please enter QR Code';
                              } else {
                                return await getRemoveDevice(val).then((value) {
                                  if (value) {
                                    setState(() {
                                      error = '';
                                    });
                                    return null;
                                  } else {
                                    setState(() {
                                      error = 'Component found';
                                    });
                                    return 'Component not found';
                                  }
                                });
                              }
                            },
                            labelText: 'QR Code',
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
                                    headingRowAlignment:
                                        MainAxisAlignment.center,
                                    label: Expanded(
                                      child: Text(
                                        '',
                                        style: TextStyle(
                                            fontStyle: FontStyle.italic),
                                      ),
                                    ),
                                  ),
                                  DataColumn(
                                    label: Expanded(
                                      child: Text(
                                        '',
                                        style: TextStyle(
                                            fontStyle: FontStyle.italic),
                                      ),
                                    ),
                                  ),
                                ],
                                rows: <DataRow>[
                                  DataRow(
                                    cells: <DataCell>[
                                      DataCell(Text("Component Code")),
                                      DataCell(Text(
                                          hhDeviceResponse!
                                                  .data!.componentCode ??
                                              '',
                                          style: TextStyle(
                                              fontWeight: FontWeight.bold))),
                                    ],
                                  ),
                                  DataRow(
                                    cells: <DataCell>[
                                      DataCell(Text("Component Type")),
                                      DataCell(Text(
                                          hhDeviceResponse!
                                                  .data!.componentType ??
                                              '',
                                          style: TextStyle(
                                              fontWeight: FontWeight.bold))),
                                    ],
                                  ),
                                  DataRow(
                                    cells: <DataCell>[
                                      DataCell(Text("Component Status")),
                                      DataCell(getStatus(hhDeviceResponse!
                                          .data!.componentStatus)),
                                    ],
                                  ),
                                  DataRow(
                                    cells: <DataCell>[
                                      DataCell(Text("HH Device Code")),
                                      DataCell(Text(
                                          hhDeviceResponse!
                                                  .data!.hhDeviceCode ??
                                              '',
                                          style: TextStyle(
                                              fontWeight: FontWeight.bold))),
                                    ],
                                  ),
                                ]),
                            SizedBox(
                              height: 10,
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
    ComponentVerificationResp? resp =
        await _hhDeviceService.verifyComponent(val);

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
