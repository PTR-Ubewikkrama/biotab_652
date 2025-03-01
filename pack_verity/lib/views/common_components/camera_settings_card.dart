import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:pack_verity/service_locator.dart';

class CameraSettingsCard {
  final String zoomKey;
  final double zoomVal;
  final String flashKey;
  final bool flashVal;
  final String scaleKey;
  final double scaleVal;
  final String title;

  CameraSettingsCard(
      {required this.zoomKey,
      required this.flashVal,
      required this.flashKey,
      required this.scaleVal,
      required this.zoomVal,
      required this.scaleKey,
      required this.title});
}

class CameraSettingsCardCustom extends StatefulWidget {
  final CameraSettingsCard data;
  const CameraSettingsCardCustom({Key? key, required this.data})
      : super(key: key);

  @override
  State<CameraSettingsCardCustom> createState() =>
      _CameraSettingsCardCustomState();
}

class _CameraSettingsCardCustomState extends State<CameraSettingsCardCustom> {
  late List<bool> isSelected;
  late double _zoolLevel;
  late double _scanAreaVal;

  @override
  void initState() {
    isSelected = widget.data.flashVal ? [false, true] : [true, false];
    _zoolLevel = widget.data.zoomVal;
    _scanAreaVal = widget.data.scaleVal;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    double screenHeight = MediaQuery.of(context).size.height;
    double screenWidth = MediaQuery.of(context).size.width;

    return Padding(
        padding: const EdgeInsets.all(8.0),
        child: Container(
          decoration: BoxDecoration(
            gradient: const LinearGradient(
              begin: Alignment.centerLeft,
              end: Alignment.centerRight,
              colors: [
                Colors.white,
                Colors.white
              ], // Replace with your desired colors
            ),
            borderRadius: BorderRadius.circular(10.0),
            boxShadow: [
              BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                spreadRadius: 3,
                blurRadius: 10,
                offset: const Offset(0, 0.5), // changes position of shadow
              ),
            ],
          ),
          height: (screenHeight / 7) * 1.3,
          width: screenWidth - 20,
          child: Padding(
            padding: const EdgeInsets.only(left: 10.0, right: 10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Padding(
                  padding: const EdgeInsets.only(top: 8.0),
                  child: Text(
                    widget.data.title,
                    style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 20,
                        color: Colors.grey),
                  ),
                ),
                const SizedBox(
                  height: 10,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    const Text(
                      "Flash : ",
                      style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18,
                          color: Colors.black54),
                    ),
                    const SizedBox(
                      width: 25,
                    ),
                    ToggleButtons(
                      direction: Axis.horizontal,
                      onPressed: (int index) async {
                        setState(() {
                          // The button that is tapped is set to true, and the others to false.
                          // Update the selection to allow only one button to be selected
                          for (int buttonIndex = 0;
                              buttonIndex < isSelected.length;
                              buttonIndex++) {
                            isSelected[buttonIndex] = buttonIndex == index;
                          }
                        });
                        await sl.get<FlutterSecureStorage>().write(
                            key: widget.data.flashKey,
                            value: index == 0 ? "false" : "true");
                      },
                      borderRadius: const BorderRadius.all(Radius.circular(5)),
                      selectedBorderColor: Colors.green[700],
                      selectedColor: Colors.white,
                      fillColor: Colors.green,
                      color: Colors.red[400],
                      constraints: const BoxConstraints(
                        minHeight: 25.0,
                        minWidth: 40.0,
                      ),
                      isSelected: isSelected,
                      children: const [
                        Text(
                          "OFF",
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                        Text(
                          "ON",
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                  ],
                ),
                SizedBox(
                  height: 25,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      const Expanded(
                        flex: 2,
                        child: Text(
                          "Zoom Level : ",
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 18,
                              color: Colors.black54),
                        ),
                      ),
                      Expanded(
                        flex: 3,
                        child: Slider(
                          thumbColor: Colors.green,
                          value: _zoolLevel,
                          activeColor: Colors.lightGreenAccent,
                          inactiveColor: Colors.grey,
                          onChanged: (newValue) async {
                            setState(() {
                              _zoolLevel = newValue;
                            });
                            await sl.get<FlutterSecureStorage>().write(
                                key: widget.data.zoomKey,
                                value: newValue.toString());
                          },
                          min: 0.0,
                          max: 1.0,
                          divisions: 100,
                        ),
                      ),
                      Expanded(child: Text((_zoolLevel).toString()))
                    ],
                  ),
                ),
                SizedBox(
                  height: 35,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      const Expanded(
                        flex: 2,
                        child: Text(
                          "Scan Area : ",
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 18,
                              color: Colors.black54),
                        ),
                      ),
                      Expanded(
                        flex: 3,
                        child: Slider(
                          thumbColor: Colors.blue,
                          value: _scanAreaVal,
                          activeColor: Colors.lightBlueAccent,
                          inactiveColor: Colors.grey,
                          onChanged: (newValue) async {
                            setState(() {
                              _scanAreaVal = newValue;
                            });
                            await sl.get<FlutterSecureStorage>().write(
                                key: widget.data.scaleKey,
                                value: newValue.toString());
                          },
                          min: 0.0,
                          max: 1.0,
                          divisions: 10,
                        ),
                      ),
                      Expanded(child: Text((_scanAreaVal).toString()))
                    ],
                  ),
                ),
              ],
            ),
          ),
        ));
  }
}
