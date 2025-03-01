import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/common_utils.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/camera_settings_card.dart';

class SettingsView extends StatefulWidget {
  const SettingsView({
    Key? key,
  }) : super(key: key);

  @override
  // ignore: no_logic_in_create_state
  State<SettingsView> createState() => _SettingsState();
}

class _SettingsState extends State<SettingsView> {
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
            'Camera settings',
            style: const TextStyle(
                color: Colors.white, fontWeight: FontWeight.bold),
          ),
        ),
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(12.0),
            child: Column(
              children: [
                FutureBuilder(
                    future: getZoomVal(cameraZoomLevel, cameraScanAreaScale,
                        cameraFlashStatus),
                    builder: (context, snapshot) {
                      if (snapshot.hasData) {
                        return CameraSettingsCardCustom(
                          data: CameraSettingsCard(
                              zoomKey: cameraZoomLevel,
                              flashVal: snapshot.data!.flashVal,
                              flashKey: cameraFlashStatus,
                              scaleVal: snapshot.data!.scaleVal,
                              zoomVal: snapshot.data!.zoomVal,
                              scaleKey: cameraScanAreaScale,
                              title: "QR Scan"),
                        );
                      }
                      return Container();
                    }),
              ],
            ),
          ),
        ));
  }
}
