import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/domain/common_resp.dart';
import 'package:pack_verity/domain/validate_component_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/hh_device_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/common_utils.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/common_popups.dart';
import 'package:pack_verity/views/common_components/qr_scan.dart';
import 'package:pack_verity/views/common_components/text_input.dart';

class AddHHBoxPage extends StatefulWidget {
  const AddHHBoxPage({super.key});

  @override
  State<AddHHBoxPage> createState() => _AddHHBoxPageState();
}

class _AddHHBoxPageState extends State<AddHHBoxPage> {
  final _formKey = GlobalKey<FormState>();
  int _index = 0;
  final List<TextEditingController> _controllers =
      List.generate(11, (index) => TextEditingController());
  final List<String> _errors = List.generate(11, (index) => '');
  final HhDeviceService _hhDeviceService = sl.get<HhDeviceService>();
  final List<String> _componentStatus = List.generate(11, (index) => '');
  bool isSubmitting = false;
  bool isForceSubmitClicked = false;

  @override
  void dispose() {
    for (final controller in _controllers) {
      controller.dispose();
    }
    super.dispose();
  }

  void _submitForm() {
    setState(() {
      isSubmitting = true;
    });
    Map<String, String> data = {
      'deviceCode': _controllers[0].text,
      'deviceCodeStatus': _componentStatus[0],
      'pcbTestCode': _controllers[1].text,
      'pcbTestCodeStatus': _componentStatus[1],
      'valveTestOneCode': _controllers[2].text,
      'valveTestOneCodeStatus': _componentStatus[2],
      'valveTestTwoCode': _controllers[3].text,
      'valveTestTwoCodeStatus': _componentStatus[3],
      'airPumpTestCode': _controllers[4].text,
      'airPumpTestCodeStatus': _componentStatus[4],
      'latchButtonTestCode': _controllers[5].text,
      'latchButtonTestCodeStatus': _componentStatus[5],
      'overPressureValveTestCode': _controllers[6].text,
      'overPressureValveTestCodeStatus': _componentStatus[6],
      'batteryTestCode': _controllers[7].text,
      'batteryTestCodeStatus': _componentStatus[7],
      'enclosureCode': _controllers[8].text,
      'enclosureCodeStatus': _componentStatus[8],
      'airBladderCode': _controllers[9].text,
      'airBladderCodeStatus': _componentStatus[9],
      'powerSupplyTestCode': _controllers[10].text,
      'powerSupplyTestCodeStatus': _componentStatus[10],
    };

    _hhDeviceService.addHhDevice(data).then((value) {
      if (value != null && value.isSuccess()) {
        handleSuccessC(context, "Saving success");
        resetForm();
      }
    }).catchError((error) {
      handleError(context, error.toString());
    }).whenComplete(() {
      setState(() {
        isSubmitting = false;
        isForceSubmitClicked = false;
      });
    });
  }

  void resetForm() {
    for (final controller in _controllers) {
      controller.clear();
    }
    _errors.forEach((element) {
      element = '';
    });
    _componentStatus.forEach((element) {
      element = '';
    });
    setState(() {
      _index = 0;
    });
  }

  String getStepTitles(int index) {
    switch (index) {
      case 0:
        return 'HH Device';
      case 1:
        return 'PCB';
      case 2:
        return 'Valve One ';
      case 3:
        return 'Valve Two';
      case 4:
        return 'Air Pump';
      case 5:
        return 'Power Button';
      case 6:
        return 'Over Pressure Valve';
      case 7:
        return 'Battery';
      case 8:
        return 'Encloser';
      case 9:
        return 'Air Bladder';
      case 10:
        return 'Power Adapter';
      default:
        return '';
    }
  }

  void setScannedValue(String value, int index) {
    _controllers[index].text = value;
    _formKey.currentState!.validate();
  }

  void ignoreErrorsAndNext() {
    if ([8, 9, 10].contains(_index) || _controllers[_index].text.isNotEmpty) {
      _componentStatus[_index] = 'NOT VERIFIED';

      if (_index < 10) {
        setState(() {
          _index += 1;
        });
      } else {
        setState(() {
          isForceSubmitClicked = true;
        });
        _submitForm();
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
            duration: Duration(seconds: 2),
            backgroundColor: Colors.red,
            content: Text('Please Fix the issues before proceeding.')),
      );
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
          'Add HH Device',
          style:
              const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
        ),
      ),
      body: Center(
        child: Form(
          key: _formKey,
          child: Stepper(
            physics: ClampingScrollPhysics(),
            currentStep: _index,
            controlsBuilder: (BuildContext context, ControlsDetails details) {
              return Padding(
                padding: const EdgeInsets.only(top: 10),
                child: Row(
                  children: <Widget>[
                    (_index != 10 && _errors[_index].isEmpty)
                        ? Expanded(
                            flex: 1,
                            child: CupertinoButton(
                              padding: EdgeInsets.all(0),
                              color: Colors.green,
                              onPressed: isSubmitting
                                  ? null
                                  : () {
                                      details.onStepContinue!();
                                    },
                              child: isSubmitting && !isForceSubmitClicked
                                  ? const CircularProgressIndicator(
                                      valueColor: AlwaysStoppedAnimation<Color>(
                                          Colors.white),
                                    )
                                  : Text(_index == 10 ? 'Submit' : 'Next',
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 14)),
                            ),
                          )
                        : SizedBox.shrink(),
                    SizedBox(
                      width: 5,
                    ),
                    (_index == 10 && _errors.any((error) => error.isNotEmpty))
                        ? Expanded(
                            flex: 2,
                            child: CupertinoButton(
                              padding: EdgeInsets.all(0),
                              color: colorInputField,
                              disabledColor: Colors.grey,
                              onPressed: isSubmitting
                                  ? null
                                  : () {
                                      ignoreErrorsAndNext();
                                    },
                              child: isSubmitting && isForceSubmitClicked
                                  ? const CircularProgressIndicator(
                                      valueColor: AlwaysStoppedAnimation<Color>(
                                          Colors.white),
                                    )
                                  : Text(
                                      _index == 10
                                          ? 'Ignore Errors & Submit'
                                          : 'Ignore Errors & Next',
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 14)),
                            ),
                          )
                        : Expanded(
                            flex: 2,
                            child: CupertinoButton(
                              padding: EdgeInsets.all(0),
                              color: colorInputField,
                              disabledColor: Colors.grey,
                              onPressed: isSubmitting
                                  ? null
                                  : () {
                                      ignoreErrorsAndNext();
                                    },
                              child: isSubmitting && isForceSubmitClicked
                                  ? const CircularProgressIndicator(
                                      valueColor: AlwaysStoppedAnimation<Color>(
                                          Colors.white),
                                    )
                                  : Text(
                                      _index == 10
                                          ? 'Ignore Errors & Submit'
                                          : 'Ignore Errors & Next',
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 14)),
                            ),
                          ),
                    SizedBox(
                      width: 5,
                    ),
                    if (_index > 0)
                      Expanded(
                        flex: 1,
                        child: CupertinoButton(
                          padding: EdgeInsets.all(0),
                          color: Colors.grey,
                          disabledColor: Colors.grey,
                          onPressed: isSubmitting ? null : details.onStepCancel,
                          child: const Text('Back',
                              style:
                                  TextStyle(color: Colors.white, fontSize: 14)),
                        ),
                      ),
                  ],
                ),
              );
            },
            onStepCancel: () {
              if (_index > 0) {
                setState(() {
                  _index -= 1;
                });
              }
            },
            onStepContinue: () {
              _formKey.currentState!.validate();
              if (_controllers[_index].text.isNotEmpty &&
                  _errors[_index] == '') {
                _componentStatus[_index] = 'VERIFIED';
                if (_index < 10) {
                  setState(() {
                    _index += 1;
                  });
                } else {
                  _submitForm();
                }
              } else {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                      duration: Duration(seconds: 2),
                      backgroundColor: Colors.red,
                      content:
                          Text('Please Fix the issues before proceeding.')),
                );
              }
            },
            onStepTapped: (int index) {
              if ((_controllers[_index].text.isNotEmpty || index <= _index) &&
                  _errors[_index] == '') {
                setState(() {
                  _index = index;
                });
              } else {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                      duration: Duration(seconds: 2),
                      backgroundColor: Colors.red,
                      content: Text('Please complete the previous steps.')),
                );
              }
            },
            steps: List<Step>.generate(11, (index) {
              return Step(
                title: Text(getStepTitles(index)),
                subtitle: _controllers[index].text.isNotEmpty
                    ? Row(
                        children: [
                          Text(
                            _controllers[index].text,
                            style: TextStyle(
                                fontWeight: FontWeight.bold, fontSize: 15),
                          ),
                          SizedBox(
                            width: 15,
                          ),
                          Text(
                            _componentStatus[index],
                            style: TextStyle(
                                color: _componentStatus[index] == 'VERIFIED'
                                    ? Colors.green
                                    : Colors.red,
                                fontWeight: FontWeight.bold,
                                fontSize: 12),
                          ),
                        ],
                      )
                    : null,
                content: Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Expanded(
                      flex: 5,
                      child: TextInputCustom(
                        data: TestInputData(
                          controller: _controllers[index],
                          validatorFun: null,
                          asyncValidatorFun: (val) async {
                            if (val!.isEmpty) {
                              _errors[index] =
                                  '${getStepTitles(index)} Code cannot be empty';
                              return '${getStepTitles(index)} Code cannot be empty';
                            } else {
                              return await validateByRemote(val, index)
                                  .then((value) {
                                if (index == 0 &&
                                    !value['status'] &&
                                    value['code'] == "E1200") {
                                  handleErrorHHDeviceAlreadyPresent(
                                    context,
                                    "HH Device already added. Do you want to delete and add again?",
                                    val,
                                  );
                                  _errors[index] =
                                      value['HH Device already added'];
                                  return value['HH Device already added'];
                                }

                                if (index == 3 && _controllers[2].text == val) {
                                  _errors[index] =
                                      'Valve Two Code cannot be same as Valve One Code';
                                  return 'Valve Two Code cannot be same as Valve One Code';
                                }

                                if (index == 2 && _controllers[3].text == val) {
                                  _errors[index] =
                                      'Valve One Code cannot be same as Valve Two Code';
                                  return 'Valve One Code cannot be same as Valve Two Code';
                                }

                                if (!value['status']) {
                                  _errors[index] =
                                      value['status'] ? '' : value['message'];
                                  return value['message'];
                                } else {
                                  _errors[index] = '';
                                  return null;
                                }
                              });
                            }
                          },
                          labelText: '${getStepTitles(index)} Code ...',
                        ),
                      ),
                    ),
                    FutureBuilder(
                        future: getZoomVal(cameraZoomLevel, cameraScanAreaScale,
                            cameraFlashStatus),
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
                                              builder: (context) => QRScanView(
                                                  setScannedValue,
                                                  index: index,
                                                  areaScale:
                                                      snapshot.data!.scaleVal,
                                                  flashStatus:
                                                      snapshot.data!.flashVal,
                                                  zoomScale:
                                                      snapshot.data!.zoomVal)));
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
                isActive: _index >= index,
              );
            }),
          ),
        ),
      ),
    );
  }

  Future<Map<String, dynamic>> validateByRemote(String val, int index) async {
    switch (index) {
      case 0:
        CommonResponse? resp =
            await _hhDeviceService.validateHHDeviceByCode(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {
            'status': false,
            'message': resp!.statusDescription,
            'code': resp.status
          };
        }
      case 1:
        ValidateComponentResp? resp =
            await _hhDeviceService.validatePcbTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 2:
        ValidateComponentResp? resp =
            await _hhDeviceService.validateValveTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 3:
        ValidateComponentResp? resp =
            await _hhDeviceService.validateValveTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 4:
        ValidateComponentResp? resp =
            await _hhDeviceService.validateAirPumpTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 5:
        ValidateComponentResp? resp =
            await _hhDeviceService.validateLatchButtonTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 6:
        ValidateComponentResp? resp =
            await _hhDeviceService.validateOverPressureValveTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 7:
        ValidateComponentResp? resp =
            await _hhDeviceService.validateBatteryTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 8:
        return {'status': true, 'message': 'Valid Encloser Code'};
      case 9:
        return {'status': true, 'message': 'Valid Air Bladder Code'};
      case 10:
        ValidateComponentResp? resp =
            await _hhDeviceService.validatePowerSupplyTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      default:
    }

    return {'status': false, 'message': 'Invalid Code'};
  }
}
