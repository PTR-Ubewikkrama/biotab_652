import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/hh_device_service.dart';
import 'package:pack_verity/views/auth/login/login.dart';

handleError(BuildContext context, String s) {
  AwesomeDialog(
    context: context,
    dialogType: DialogType.error,
    animType: AnimType.bottomSlide,
    headerAnimationLoop: false,
    body: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          const Center(
            child: Text(
              "Failed",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
          const SizedBox(
            height: 5,
          ),
          Center(
            child: Text(s),
          ),
          const SizedBox(
            height: 20,
          ),
          Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.red,
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: const Text("Close"),
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
        ],
      ),
    ),
  ).show();
}

handleErrorHHDeviceAlreadyPresent(
    BuildContext context, String s, String deviceCode) {
  AwesomeDialog(
    context: context,
    dialogType: DialogType.error,
    animType: AnimType.bottomSlide,
    headerAnimationLoop: false,
    body: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          const Center(
            child: Text(
              "Failed",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
          const SizedBox(
            height: 5,
          ),
          Center(
            child: Text(s),
          ),
          const SizedBox(
            height: 20,
          ),
          Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.red,
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: const Text("Cancel"),
                  ),
                ),
              ),
              const SizedBox(
                width: 10,
              ),
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.green,
                    onPressed: () {
                      HhDeviceService _hhDeviceService =
                          sl.get<HhDeviceService>();
                      _hhDeviceService
                          .deleteHhDeviceByCode(deviceCode)
                          .then((value) {
                        Navigator.of(context).pop();
                      });
                    },
                    child: const Text("Delete and Add"),
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
        ],
      ),
    ),
  ).show();
}

handleSuccessC(BuildContext context, String s) {
  AwesomeDialog(
    context: context,
    dialogType: DialogType.success,
    animType: AnimType.bottomSlide,
    headerAnimationLoop: false,
    body: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          const Center(
            child: Text(
              "Success",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
          const SizedBox(
            height: 5,
          ),
          Center(
            child: Text(s),
          ),
          const SizedBox(
            height: 20,
          ),
          Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.green,
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: const Text("Close"),
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
        ],
      ),
    ),
  ).show();
}

handlePasswordResetSuccess(BuildContext context, String s) {
  AwesomeDialog(
    context: context,
    dialogType: DialogType.success,
    animType: AnimType.bottomSlide,
    headerAnimationLoop: false,
    body: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          const Center(
            child: Text(
              "Success",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
          const SizedBox(
            height: 5,
          ),
          Center(
            child: Text(s),
          ),
          const SizedBox(
            height: 20,
          ),
          Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.green,
                    onPressed: () {
                      Navigator.of(context).pop();
                      Navigator.pushAndRemoveUntil(context,
                          MaterialPageRoute(builder: (context) {
                        return const LoginPage();
                      }), (route) => false);
                    },
                    child: const Text("Close"),
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
        ],
      ),
    ),
  ).show();
}

handleWarning(BuildContext context, String s, VoidCallback onDelete) {
  AwesomeDialog(
    context: context,
    dialogType: DialogType.warning,
    animType: AnimType.bottomSlide,
    headerAnimationLoop: false,
    body: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          const Center(
            child: Text(
              "Warning",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
          const SizedBox(
            height: 5,
          ),
          Center(
            child: Text(s),
          ),
          const SizedBox(
            height: 20,
          ),
          Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.red,
                    onPressed: () {
                      onDelete();
                      Navigator.of(context).pop();
                    },
                    child: const Text("Delete"),
                  ),
                ),
              ),
              const SizedBox(
                width: 10,
              ),
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.orange,
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: const Text("Close"),
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
        ],
      ),
    ),
  ).show();
}

handleWarningCartoonBoxAlreadyThere(
    BuildContext context, String s, VoidCallback onDelete) {
  AwesomeDialog(
    context: context,
    dialogType: DialogType.warning,
    animType: AnimType.bottomSlide,
    headerAnimationLoop: false,
    body: Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          const Center(
            child: Text(
              "Warning",
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
          const SizedBox(
            height: 5,
          ),
          Center(
            child: Text(s),
          ),
          const SizedBox(
            height: 20,
          ),
          Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.red,
                    onPressed: () {
                      onDelete();
                      Navigator.of(context).pop();
                    },
                    child: const Text("Update"),
                  ),
                ),
              ),
              const SizedBox(
                width: 10,
              ),
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    padding: const EdgeInsets.all(0),
                    color: Colors.orange,
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: const Text("Close"),
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
        ],
      ),
    ),
  ).show();
}

handleSuccess(BuildContext context, String s) {
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return AlertDialog(
        shape: OutlineInputBorder(borderRadius: BorderRadius.circular(16.0)),
        insetPadding: const EdgeInsets.all(0),
        elevation: 10,
        title: Center(child: Text(s)),
        actions: <Widget>[
          Row(
            children: [
              Expanded(
                child: SizedBox(
                  height: 50,
                  child: CupertinoButton(
                    color: Colors.green,
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: const Text("Close"),
                  ),
                ),
              ),
            ],
          ),
        ],
      );
    },
  );
}
