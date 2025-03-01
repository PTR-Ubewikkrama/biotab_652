import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/domain/common_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/fa_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/common_utils.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/common_popups.dart';
import 'package:pack_verity/views/common_components/qr_scan.dart';
import 'package:pack_verity/views/common_components/text_input.dart';

class StageThreePage extends StatefulWidget {
  const StageThreePage({super.key});

  @override
  State<StageThreePage> createState() => _StageThreePageState();
}

class _StageThreePageState extends State<StageThreePage> {
  final _formKey = GlobalKey<FormState>();
  int _index = 0;
  final List<TextEditingController> _controllers =
      List.generate(3, (index) => TextEditingController());
  final List<String> _errors = List.generate(3, (index) => '');
  final FAService faService = sl.get<FAService>();
  bool isSubmitting = false;
  bool isForceSubmitClicked = false;
  bool skipIgnore = false;

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
      'uplNumber': _controllers[0].text,
      'udiNumber': _controllers[1].text,
      'adapterCode': _controllers[2].text
    };

    faService.addFinalAssemblyStepThree(data).then((value) {
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
        skipIgnore = false;
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
    setState(() {
      _index = 0;
    });
  }

  String getStepTitles(int index) {
    switch (index) {
      case 0:
        return 'UPL Number';
      case 1:
        return 'UDI Number';
      case 2:
        return 'Adapter Code';
      default:
        return '';
    }
  }

  void setScannedValue(String value, int index) {
    _controllers[index].text = value;
    _formKey.currentState!.validate();
  }

  void ignoreErrorsAndNext() {
    if (_controllers[_index].text.isNotEmpty) {
      if (_index < 2) {
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
          'Stage Three',
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
                    (_index != 2 && _errors[_index].isEmpty)
                        ? Expanded(
                            flex: 1,
                            child: CupertinoButton(
                              padding: EdgeInsets.all(0),
                              color: Colors.green,
                              onPressed: isSubmitting || skipIgnore
                                  ? null
                                  : () {
                                      details.onStepContinue!();
                                    },
                              child: isSubmitting && !isForceSubmitClicked
                                  ? const CircularProgressIndicator(
                                      valueColor: AlwaysStoppedAnimation<Color>(
                                          Colors.white),
                                    )
                                  : Text(_index == 2 ? 'Submit' : 'Next',
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 14)),
                            ),
                          )
                        : SizedBox.shrink(),
                    SizedBox(
                      width: 5,
                    ),
                    (_index == 2 && _errors.any((error) => error.isNotEmpty))
                        ? Expanded(
                            flex: 2,
                            child: CupertinoButton(
                              padding: EdgeInsets.all(0),
                              color: colorInputField,
                              disabledColor: Colors.grey,
                              onPressed: isSubmitting || skipIgnore
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
                                      _index == 2
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
                              onPressed: isSubmitting || skipIgnore
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
                                      _index == 2
                                          ? 'Submit'
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
                if (_index < 2) {
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
            steps: List<Step>.generate(3, (index) {
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
                                if (!value['status']) {
                                  _errors[index] =
                                      value['status'] ? '' : value['message'];
                                  setState(() {
                                    skipIgnore = true;
                                  });
                                  return value['message'];
                                } else {
                                  _errors[index] = '';
                                  setState(() {
                                    skipIgnore = false;
                                  });
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
    Map<String, String> data = {'code': val};
    switch (index) {
      case 0:
        CommonResponse? resp =
            await faService.validateUPLNumberForStepThree(data);
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
        CommonResponse? resp = await faService.validateUDINumber(data);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {'status': false, 'message': resp!.statusDescription};
        }
      case 2:
        CommonResponse? resp = await faService.validateAdapterCode(data);
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
