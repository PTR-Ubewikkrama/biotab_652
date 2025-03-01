import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/fa_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/common_utils.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/common_popups.dart';
import 'package:pack_verity/views/common_components/qr_scan.dart';
import 'package:pack_verity/views/common_components/text_input.dart';

class CartoonBoxPage extends StatefulWidget {
  const CartoonBoxPage({super.key});

  @override
  State<CartoonBoxPage> createState() => _CartoonBoxPageState();
}

class _CartoonBoxPageState extends State<CartoonBoxPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController qrCodeController = TextEditingController();
  String error = '';
  final List<TextEditingController> _controllers = List.empty(growable: true);
  final List<String> _errors = List.empty(growable: true);
  final FAService faService = sl.get<FAService>();
  bool isSubmitting = false;
  bool isForceSubmitClicked = false;
  int? selectedIndex = 0;
  bool hasErrors = false;
  bool isUpdate = false;
  bool isPopUpShown = false;

  @override
  void dispose() {
    super.dispose();
  }

  void resetForm() {
    qrCodeController.clear();
    setState(() {
      error = '';
    });
    _controllers.clear();
  }

  void setScannedValue(String value, int index) {
    qrCodeController.text = value;
    _formKey.currentState!.validate();
  }

  void setScannedValueDevices(String value, int index) {
    _controllers[index].text = value;
    _formKey.currentState!.validate();
  }

  Future<void> getCartoonBoxDetails() async {
    setState(() {
      isPopUpShown = false;
    });
    Map<String, String> data = {'code': qrCodeController.text};

    try {
      final result = await faService.getCartoonPackageByCartoonNumber(data);

      if (!mounted) return;

      if (result != null && result.isSuccess()) {
        setState(() {
          _controllers.clear();
          result.data!.uids!.forEach((element) {
            _controllers.add(TextEditingController(text: element));
          });
          isUpdate = true;
        });
      } else {
        handleError(context, "Something went wrong. Please try again");
      }
    } catch (_) {
      handleError(context, "Something went wrong. Please try again");
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
          'Add Cartoon Box',
          style:
              const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
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
                              setState(() {
                                _controllers.clear();
                                isUpdate = false;
                              });

                              if (val!.isEmpty) {
                                setState(() {
                                  if (!_errors.contains('CN')) {
                                    _errors.add('CN');
                                  }
                                  error = 'Please enter Cartoon number';
                                });
                                return 'Please enter Cartoon number';
                              } else {
                                Map<String, String> data = {
                                  'code': qrCodeController.text
                                };
                                return await faService
                                    .validateCartoonBoxCartoonNumber(data)
                                    .then((value) {
                                  if (value != null && value.isSuccess()) {
                                    setState(() {
                                      if (_errors.contains('CN')) {
                                        _errors.remove('CN');
                                      }
                                      error = '';
                                    });
                                    return null;
                                  } else {
                                    if (value!.status == "E2222") {
                                      if (isPopUpShown) {
                                        return null;
                                      }
                                      handleWarningCartoonBoxAlreadyThere(
                                          context,
                                          "Cartoon Box already exists. Are you sure you want to update? This cannot be undone.",
                                          getCartoonBoxDetails);
                                      setState(() {
                                        isPopUpShown = true;
                                      });
                                    } else {
                                      setState(() {
                                        error = value.statusDescription;
                                        if (!_errors.contains('CN')) {
                                          _errors.add('CN');
                                        }
                                      });
                                      return value.statusDescription;
                                    }
                                  }
                                });
                              }
                            },
                            labelText: 'Cartoon Number',
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
                    height: 25,
                  ),
                  Row(
                    children: [
                      Expanded(
                        flex: 7,
                        child: Text(
                          "Items section",
                          style: TextStyle(
                              color: Colors.grey, fontWeight: FontWeight.bold),
                        ),
                      ),
                      Expanded(
                        flex: 1,
                        child: SizedBox(
                          height: 25,
                          child: CupertinoButton(
                              padding: const EdgeInsets.all(0),
                              color: Colors.green,
                              onPressed: _controllers.any(
                                          (element) => element.text.isEmpty) &&
                                      hasErrors
                                  ? null
                                  : () {
                                      setState(() {
                                        _controllers
                                            .add(TextEditingController());
                                      });
                                    },
                              child: const Icon(
                                Icons.add,
                                color: Colors.white,
                              )),
                        ),
                      )
                    ],
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  SingleChildScrollView(
                    child: SizedBox(
                      height: MediaQuery.of(context).size.height - 350,
                      child: ListView.builder(
                          shrinkWrap: true,
                          itemCount: _controllers.length,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.only(bottom: 8),
                              child: Row(
                                children: [
                                  Expanded(
                                    flex: 7,
                                    child: TextInputCustom(
                                      data: TestInputData(
                                        controller: _controllers[index],
                                        validatorFun: null,
                                        asyncValidatorFun: (val) async {
                                          if (val!.isEmpty) {
                                            setState(() {
                                              if (!_errors
                                                  .contains('C$index')) {
                                                _errors.add('C$index');
                                              }
                                            });
                                            return 'Please enter UDI';
                                          } else {
                                            Map<String, String> data = {
                                              'code': _controllers[index].text
                                            };
                                            return await faService
                                                .validateCartoonBoxUDINumber(
                                                    data)
                                                .then((value) {
                                              if (value != null &&
                                                  value.isSuccess()) {
                                                setState(() {
                                                  if (_errors
                                                      .contains('C$index')) {
                                                    _errors.remove('C$index');
                                                  }
                                                  hasErrors = false;
                                                });
                                                return null;
                                              } else {
                                                if (value!.status == "E2222" &&
                                                    isUpdate) {
                                                  setState(() {
                                                    if (_errors
                                                        .contains('C$index')) {
                                                      _errors.remove('C$index');
                                                    }
                                                    hasErrors = false;
                                                  });
                                                  return null;
                                                } else {
                                                  setState(() {
                                                    if (!_errors
                                                        .contains('C$index')) {
                                                      _errors.add('C$index');
                                                    }
                                                    hasErrors = true;
                                                  });
                                                  return value
                                                      .statusDescription;
                                                }
                                              }
                                            });
                                          }
                                        },
                                        labelText: 'UDI Number',
                                      ),
                                    ),
                                  ),
                                  FutureBuilder(
                                      future: getZoomVal(
                                          cameraZoomLevel,
                                          cameraScanAreaScale,
                                          cameraFlashStatus),
                                      builder: (context, snapshot) {
                                        if (snapshot.hasData) {
                                          return Expanded(
                                            flex: 2,
                                            child: Padding(
                                              padding: const EdgeInsets.only(
                                                  left: 8.0, top: 0),
                                              child: SizedBox(
                                                height: 55,
                                                child: CupertinoButton(
                                                  padding:
                                                      const EdgeInsets.all(0),
                                                  color: Colors.black,
                                                  child: const Icon(
                                                    Icons.qr_code,
                                                    color: Colors.white,
                                                  ),
                                                  onPressed: () {
                                                    Navigator.push(
                                                        context,
                                                        MaterialPageRoute(
                                                            builder: (context) => QRScanView(
                                                                setScannedValueDevices,
                                                                areaScale:
                                                                    snapshot
                                                                        .data!
                                                                        .scaleVal,
                                                                flashStatus:
                                                                    snapshot
                                                                        .data!
                                                                        .flashVal,
                                                                zoomScale: snapshot
                                                                    .data!
                                                                    .zoomVal)));
                                                  },
                                                ),
                                              ),
                                            ),
                                          );
                                        } else {
                                          return Expanded(
                                              flex: 1, child: Container());
                                        }
                                      }),
                                  SizedBox(
                                    width: 5,
                                  ),
                                  Expanded(
                                    flex: 1,
                                    child: SizedBox(
                                      height: 25,
                                      child: CupertinoButton(
                                          padding: const EdgeInsets.all(0),
                                          color: Colors.red,
                                          child: const Icon(
                                            Icons.remove,
                                            color: Colors.white,
                                          ),
                                          onPressed: () {
                                            setState(() {
                                              _controllers.removeAt(index);
                                              _errors.remove('C$index');
                                            });
                                          }),
                                    ),
                                  )
                                ],
                              ),
                            );
                          }),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
      bottomNavigationBar: Padding(
          padding: EdgeInsets.all(5),
          child: Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 55,
                  child: CupertinoButton(
                    color: Colors.black,
                    disabledColor: Colors.grey,
                    onPressed: _errors.isEmpty
                        ? () {
                            Map<String, dynamic> data = {
                              'cartoonNumber': qrCodeController.text,
                              'udiNumbers':
                                  _controllers.map((e) => e.text).toList()
                            };
                            setState(() {
                              isSubmitting = true;
                              isForceSubmitClicked = true;
                              isPopUpShown = true;
                            });
                            if (isUpdate) {
                              faService
                                  .updateCartoonPackage(data)
                                  .then((value) {
                                if (value != null && value.isSuccess()) {
                                  resetForm();
                                  qrCodeController.clear();
                                  handleSuccessC(context, "Update success");
                                } else {
                                  handleError(
                                      context, value!.statusDescription);
                                }
                              }).catchError((error) {
                                handleError(context, error.toString());
                              }).whenComplete(() {
                                setState(() {
                                  isSubmitting = false;
                                  isForceSubmitClicked = false;
                                  isPopUpShown = false;
                                });
                              });
                            } else {
                              faService.addCartoonPackage(data).then((value) {
                                if (value != null && value.isSuccess()) {
                                  resetForm();
                                  handleSuccessC(context, "Saving success");
                                } else {
                                  handleError(
                                      context, value!.statusDescription);
                                }
                              }).catchError((error) {
                                handleError(context, error.toString());
                              }).whenComplete(() {
                                setState(() {
                                  isSubmitting = false;
                                  isForceSubmitClicked = false;
                                  isPopUpShown = false;
                                });
                              });
                            }
                          }
                        : null,
                    child: Text(
                      isUpdate ? 'Update Cartoon Box' : 'Add Cartoon Box',
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                ),
              ),
            ],
          )),
    );
  }
}
