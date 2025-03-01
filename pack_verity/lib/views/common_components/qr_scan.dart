import 'package:flutter/material.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import 'package:pack_verity/utils/colors.dart';

class QRScanView extends StatefulWidget {
  final void Function(String, int) callback;
  final bool flashStatus;
  final double zoomScale;
  final double areaScale;
  final int index;
  const QRScanView(
    this.callback, {
    super.key,
    this.index = 0,
    this.areaScale = 0.2,
    this.flashStatus = false,
    this.zoomScale = 0.725,
  });

  @override
  State<QRScanView> createState() => _QRScanViewState();
}

class _QRScanViewState extends State<QRScanView> {
  double _sliderValue = 0.725;
  late MobileScannerController cameraController;

  @override
  void initState() {
    cameraController = MobileScannerController(
        detectionSpeed: DetectionSpeed.normal,
        facing: CameraFacing.back,
        torchEnabled: widget.flashStatus,
        detectionTimeoutMs: 750);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    return SafeArea(
      child: Scaffold(
        backgroundColor: Colors.black,
        appBar: AppBar(
          title: const Text('Mobile Scanner'),
          actions: [
            IconButton(
              color: colorInputField,
              icon: ValueListenableBuilder(
                valueListenable: cameraController,
                builder: (context, state, child) {
                  if (!state.isInitialized || !state.isRunning) {
                    return const SizedBox.shrink();
                  }

                  switch (state.torchState) {
                    case TorchState.auto:
                      return IconButton(
                        color: colorInputField,
                        iconSize: 32.0,
                        icon: const Icon(Icons.flash_auto),
                        onPressed: () async {
                          await cameraController.toggleTorch();
                        },
                      );
                    case TorchState.off:
                      return IconButton(
                        color: colorInputField,
                        iconSize: 32.0,
                        icon: const Icon(Icons.flash_off),
                        onPressed: () async {
                          await cameraController.toggleTorch();
                        },
                      );
                    case TorchState.on:
                      return IconButton(
                        color: colorInputField,
                        iconSize: 32.0,
                        icon: const Icon(Icons.flash_on),
                        onPressed: () async {
                          await cameraController.toggleTorch();
                        },
                      );
                    case TorchState.unavailable:
                      return const Icon(
                        Icons.no_flash,
                        color: Colors.grey,
                      );
                  }
                },
              ),
              iconSize: 32.0,
              onPressed: () => cameraController.toggleTorch(),
            ),
            IconButton(
              color: colorInputField,
              icon: ValueListenableBuilder(
                valueListenable: cameraController,
                builder: (context, state, child) {
                  if (!state.isInitialized || !state.isRunning) {
                    return const SizedBox.shrink();
                  }

                  final int? availableCameras = state.availableCameras;

                  if (availableCameras != null && availableCameras < 2) {
                    return const SizedBox.shrink();
                  }

                  final Widget icon;

                  switch (state.cameraDirection) {
                    case CameraFacing.front:
                      icon = const Icon(Icons.camera_front);
                    case CameraFacing.back:
                      icon = const Icon(Icons.camera_rear);
                  }

                  return IconButton(
                    iconSize: 32.0,
                    icon: icon,
                    onPressed: () async {
                      await cameraController.switchCamera();
                    },
                  );
                },
              ),
              iconSize: 32.0,
              onPressed: () => cameraController.switchCamera(),
            ),
            Slider(
              thumbColor: colorInputField,
              value: _sliderValue,
              activeColor: Colors.black,
              inactiveColor: colorInputField,
              onChanged: (newValue) {
                cameraController.setZoomScale(newValue);
                setState(() {
                  _sliderValue = newValue;
                });
              },
              min: 0.0,
              max: 1.0,
              divisions: 10,
            ),
          ],
        ),
        body: Stack(
          children: [
            MobileScanner(
              scanWindow: Rect.fromCenter(
                  center: Offset(screenWidth / 2, screenHeight / 2),
                  width: (screenWidth * (widget.areaScale + 0.1)),
                  height: (screenWidth * (widget.areaScale + 0.1))),
              fit: BoxFit.fill,
              controller: cameraController,
              onDetect: (capture) {
                final List<Barcode> barcodes = capture.barcodes;
                for (final barcode in barcodes) {
                  debugPrint('Barcode found! ${barcode.rawValue}');
                  widget.callback(barcode.rawValue!, widget.index);
                }
                Navigator.pop(context);
              },
              overlayBuilder: (context, constraints) {
                return Column(
                  children: [
                    Expanded(
                      flex: ((10 - (10 * widget.areaScale)) ~/ 2) + 2,
                      child: Row(
                        children: [
                          Expanded(
                            child: Opacity(
                              opacity:
                                  0.8, // Set the opacity value here (0.0 - 1.0)
                              child: Container(
                                // This is the partially transparent container
                                color: Colors.black, // or any other color
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                      flex: (10 * widget.areaScale).toInt(),
                      child: Row(
                        children: [
                          Expanded(
                            flex: (10 - (10 * widget.areaScale)) ~/ 2,
                            child: Opacity(
                              opacity:
                                  0.8, // Set the opacity value here (0.0 - 1.0)
                              child: Container(
                                // This is the partially transparent container
                                color: Colors.black, // or any other color
                              ),
                            ),
                          ),
                          Expanded(
                            flex: (10 * widget.areaScale).toInt() + 1,
                            child: Opacity(
                              opacity:
                                  0.0, // Set the opacity value here (0.0 - 1.0)
                              child: Opacity(
                                opacity: 0.0,
                                child: ClipRRect(
                                  borderRadius: BorderRadius.circular(10),
                                  child: Container(
                                    width: 150,
                                    height: 150,
                                  ),
                                ),
                              ),
                            ),
                          ),
                          Expanded(
                            flex: (10 - (10 * widget.areaScale)) ~/ 2,
                            child: Opacity(
                              opacity:
                                  0.8, // Set the opacity value here (0.0 - 1.0)
                              child: Container(
                                // This is the partially transparent container
                                color: Colors.black, // or any other color
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                      flex: ((10 - (10 * widget.areaScale)) ~/ 2) + 2,
                      child: Row(
                        children: [
                          Expanded(
                            child: Opacity(
                              opacity:
                                  0.8, // Set the opacity value here (0.0 - 1.0)
                              child: Container(
                                // This is the partially transparent container
                                color: Colors.black, // or any other color
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
