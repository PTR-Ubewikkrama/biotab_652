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
import 'package:toggle_switch/toggle_switch.dart';

class StageOnePage extends StatefulWidget {
  const StageOnePage({super.key});

  @override
  State<StageOnePage> createState() => _StageOnePageState();
}

class _StageOnePageState extends State<StageOnePage> {
  final _formKey = GlobalKey<FormState>();
  int _index = 0;
  final TextEditingController _controller = TextEditingController();
  final List<String> _errors = List.generate(11, (index) => '');
  final FAService faService = sl.get<FAService>();
  bool isSubmitting = false;
  bool isForceSubmitClicked = false;
  int? selectedIndex = 0;
  bool skipIgnore = false;

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  void _submitForm() {
    setState(() {
      isSubmitting = true;
    });
    Map<String, String> data = {
      'deviceCode': _controller.text,
      'category': selectedIndex == 0 ? 'Medium' : 'Large',
    };

    faService.addFinalAssemblyStepOne(data).then((value) {
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

  void _updateForm() {
    setState(() {
      isSubmitting = true;
    });
    Map<String, String> data = {
      'deviceCode': _controller.text,
      'category': selectedIndex == 0 ? 'Regular' : 'Large',
    };

    faService.updateFinalAssemblyStepOne(data).then((value) {
      if (value != null && value.isSuccess()) {
        handleSuccessC(context, "Update success");
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
    _controller.clear();
    setState(() {
      _index = 0;
    });
  }

  String getStepTitles(int index) {
    switch (index) {
      case 0:
        return 'HH Device';
      case 1:
        return 'Category';
      default:
        return '';
    }
  }

  void setScannedValue(String value, int index) {
    _controller.text = value;
    _formKey.currentState!.validate();
  }

  void ignoreErrorsAndNext() {
    if (_controller.text.isNotEmpty) {
      if (_index < 1) {
        setState(() {
          _index += 1;
        });
      } else {
        setState(() {
          isForceSubmitClicked = true;
        });
        if (_errors.isNotEmpty &&
            _errors[0] ==
                "HH Device already added to Final Assembly Ignore will update the existing record") {
          _updateForm();
        } else {
          _submitForm();
        }
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
          'Stage One',
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
                    (_index != 1 && _errors[_index].isEmpty)
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
                                  : Text(_index == 1 ? 'Submit' : 'Next',
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 14)),
                            ),
                          )
                        : SizedBox.shrink(),
                    SizedBox(
                      width: 5,
                    ),
                    (_index == 1 && _errors.any((error) => error.isNotEmpty))
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
                                      _index == 1
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
                                      _index == 1
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
              if (_controller.text.isNotEmpty && _errors[_index] == '') {
                if (_index < 1) {
                  setState(() {
                    _index += 1;
                  });
                } else {
                  if (_errors.isNotEmpty &&
                      _errors[0] ==
                          "HH Device already added to Final Assembly Ignore will update the existing record") {
                    _updateForm();
                  } else {
                    _submitForm();
                  }
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
              if ((_controller.text.isNotEmpty || index <= _index) &&
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
            steps: List<Step>.generate(2, (index) {
              if (index == 0) {
                return Step(
                  title: Text(getStepTitles(index)),
                  subtitle: _controller.text.isNotEmpty
                      ? Row(
                          children: [
                            Text(
                              _controller.text,
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
                            controller: _controller,
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
                                      value['code'] == "E2222") {
                                    _errors[index] =
                                        'HH Device already added to Final Assembly Ignore will update the existing record';
                                    setState(() {
                                      skipIgnore = false;
                                    });
                                    return 'HH Device already added to Final Assembly Ignore will update the existing record';
                                  }

                                  if (!value['status']) {
                                    _errors[index] =
                                        value['status'] ? '' : value['message'];
                                    setState(() {
                                      skipIgnore = true;
                                    });
                                    return value['message'];
                                  } else {
                                    setState(() {
                                      skipIgnore = false;
                                    });
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
                                                        index: index,
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
                  isActive: _index >= index,
                );
              } else {
                return Step(
                  title: Text(getStepTitles(index)),
                  subtitle: null,
                  content: Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      ToggleSwitch(
                        initialLabelIndex: 0,
                        totalSwitches: 2,
                        minHeight: 55,
                        labels: ['Regular', 'Large'],
                        onToggle: (index) {
                          selectedIndex = index;
                        },
                      ),
                    ],
                  ),
                  isActive: _index >= index,
                );
              }
            }),
          ),
        ),
      ),
    );
  }

  Future<Map<String, dynamic>> validateByRemote(String val, int index) async {
    switch (index) {
      case 0:
        Map<String, String> data = {
          'code': val,
        };
        CommonResponse? resp = await faService.validateDeviceById(data);
        if (resp != null && resp.isSuccess()) {
          return {'status': true, 'message': resp.statusDescription};
        } else {
          return {
            'status': false,
            'message': resp!.statusDescription,
            'code': resp.status
          };
        }
      default:
    }

    return {'status': false, 'message': 'Invalid Code'};
  }
}
