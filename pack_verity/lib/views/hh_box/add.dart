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

class AddBTDevicePage extends StatefulWidget {
  const AddBTDevicePage({super.key});

  @override
  State<AddBTDevicePage> createState() => _AddBTDevicePageState();
}

class _AddBTDevicePageState extends State<AddBTDevicePage> {
  final _formKey = GlobalKey<FormState>();
  int _index = 0;
  final List<TextEditingController> _controllers =
      List.generate(30, (index) => TextEditingController());
  final List<String> _errors = List.generate(30, (index) => '');
  final BTDeviceService _btDeviceService = sl.get<BTDeviceService>();
  final List<String> _componentStatus = List.generate(30, (index) => '');
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
    Map<String, dynamic> data = {
      'deviceCode': _controllers[0].text,
      'deviceCodeStatus': _componentStatus[0],
      'powerPcbCode': _controllers[1].text,
      'powerPcbCodeStatus': _componentStatus[1],
      'pumpCode': _controllers[2].text,
      'pumpCodeStatus': _componentStatus[2],
      'fanCode': _controllers[3].text,
      'fanCodeStatus': _componentStatus[3],
      'uiPcbCode': _controllers[4].text,
      'uiPcbCodeStatus': _componentStatus[4],
      'encoderCode': _controllers[5].text,
      'encoderCodeStatus': _componentStatus[5],
      'mainPcbCode': _controllers[6].text,
      'mainPcbCodeStatus': _componentStatus[6],
      'manifoldCode': _controllers[7].text,
      'manifoldCodeStatus': _componentStatus[7],
      'valveCardInsideCableSetCode': _controllers[16].text,
      'valveCardInsideCableSetCodeStatus': _componentStatus[16],
      'valveCardInputOutputCableSetCode': _controllers[17].text,
      'valveCardInputOutputCableSetCodeStatus': _componentStatus[17],
      'overPressureValveCode': _controllers[18].text,
      'overPressureValveCodeStatus': _componentStatus[18],
      'powerCableCode': _controllers[19].text,
      'uiCableCode': _controllers[20].text,
      'displayCode': _controllers[21].text,
      'frontBracketAssemblyCode': _controllers[22].text,
      'frontBracketAssemblyCodeStatus': _componentStatus[22],
      'powerAdaptorCode': _controllers[23].text,
      'powerAdaptorCodeStatus': _componentStatus[23],
      'enclosureTopCode': _controllers[24].text,
      'enclosureBottomCode': _controllers[25].text,
      'backVentCode': _controllers[26].text,
      'fanMountCode': _controllers[27].text,
      'encoderSupporterCode': _controllers[28].text,
      'pcbHolderCode': _controllers[29].text,
      'valveCards': [
        _controllers[8].text,
        _controllers[9].text,
        _controllers[10].text,
        _controllers[11].text,
        _controllers[12].text,
        _controllers[13].text,
        _controllers[14].text,
        _controllers[15].text
      ]
    };

    _btDeviceService.addBTDevice(data).then((value) {
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
        return 'BT Device';
      case 1:
        return 'Power PCB';
      case 2:
        return 'Pump';
      case 3:
        return 'Fan';
      case 4:
        return 'UI PCB';
      case 5:
        return 'Encoder';
      case 6:
        return 'Main PCB';
      case 7:
        return 'Manifold';
      case 8:
        return 'Valve Card PCB 1';
      case 9:
        return 'Valve Card PCB 2';
      case 10:
        return 'Valve Card PCB 3';
      case 11:
        return 'Valve Card PCB 4';
      case 12:
        return 'Valve Card PCB 5';
      case 13:
        return 'Valve Card PCB 6';
      case 14:
        return 'Valve Card PCB 7';
      case 15:
        return 'Valve Card PCB 8';
      case 16:
        return 'Valve Card Inside Cable Set';
      case 17:
        return 'Valve Card Input Output Cable Set';
      case 18:
        return 'Over Pressure Valve';
      case 19:
        return 'Power Cable';
      case 20:
        return 'UI Cable';
      case 21:
        return 'Display';
      case 22:
        return 'Front Bracket Assembly';
      case 23:
        return 'Power Adaptor';
      case 24:
        return 'Enclosure Top';
      case 25:
        return 'Enclosure Bottom';
      case 26:
        return 'Back Vent';
      case 27:
        return 'Fan Mount';
      case 28:
        return 'Encoder Supporter';
      case 29:
        return 'PCB Holder';
      default:
        return '';
    }
  }

  void setScannedValue(String value, int index) {
    _controllers[index].text = value;
    _formKey.currentState!.validate();
  }

  void ignoreErrorsAndNext() {
    if ([19, 20, 21, 24, 25, 26, 27, 28, 29].contains(_index) ||
        _controllers[_index].text.isNotEmpty) {
      _componentStatus[_index] = 'N/A';

      if (_index < 29) {
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
          'Add BT Device',
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
                    (_index != 29 && _errors[_index].isEmpty)
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
                                  : Text(_index == 29 ? 'Submit' : 'Next',
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 14)),
                            ),
                          )
                        : SizedBox.shrink(),
                    SizedBox(
                      width: 5,
                    ),
                    (_index == 29 && _errors.any((error) => error.isNotEmpty))
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
                                      _index == 29
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
                                      _index == 29
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
                if (_index < 29) {
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
            steps: List<Step>.generate(30, (index) {
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
                                  handleErrorBTDeviceAlreadyPresent(
                                    context,
                                    "BT Device already added. Do you want to delete and add again?",
                                    val,
                                  );
                                  _errors[index] =
                                      value['BT Device already added'];
                                  return value['BT Device already added'];
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
            await _btDeviceService.validateBTDeviceByCode(val);
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
            await _btDeviceService.validatePcbTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 2:
        ValidateComponentResp? resp =
            await _btDeviceService.validateAirPumpTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 3:
        ValidateComponentResp? resp =
            await _btDeviceService.validateFanTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 4:
        ValidateComponentResp? resp =
            await _btDeviceService.validateUIPcbTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 5:
        return {'status': true, 'message': 'Valid Encloser Code'};
      case 6:
        ValidateComponentResp? resp =
            await _btDeviceService.validateMainPcbTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 7:
        ValidateComponentResp? resp =
            await _btDeviceService.validateManiFoldTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 8 || 11 || 12 || 13 || 14 || 15:
        ValidateComponentResp? resp =
            await _btDeviceService.validateValveCardTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 16 || 17:
        return {'status': true, 'message': 'Valid Valve Card Cable Set Code'};
      case 18:
        ValidateComponentResp? resp =
            await _btDeviceService.validateOverPressureValveTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 19:
        return {'status': true, 'message': 'Valid Power Cable Code'};
      case 20:
        return {'status': true, 'message': 'Valid UI Cable Code'};
      case 21:
        ValidateComponentResp? resp =
            await _btDeviceService.validateDisplayTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 22:
        ValidateComponentResp? resp =
            await _btDeviceService.validateFrontBracketAssemblyTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 23:
        ValidateComponentResp? resp =
            await _btDeviceService.validatePowerSupplyTest(val);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 24:
        return {'status': true, 'message': 'Valid Enclosure Top Code'};
      case 25:
        return {'status': true, 'message': 'Valid Enclosure Bottom Code'};
      case 26:
        return {'status': true, 'message': 'Valid Back Vent Code'};
      case 27:
        return {'status': true, 'message': 'Valid Fan Mount Code'};
      case 28:
        return {'status': true, 'message': 'Valid Encoder Supporter Code'};
      case 29:
        return {'status': true, 'message': 'Valid PCB Holder Code'};
      default:
    }

    return {'status': false, 'message': 'Invalid Code'};
  }
}
