import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/auth_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/views/auth/login/login.dart';
import 'package:pack_verity/views/common_components/home_button_card.dart';
import 'package:pack_verity/views/final_assembly/cartoon_box.dart';
import 'package:pack_verity/views/final_assembly/stage_1.dart';
import 'package:pack_verity/views/final_assembly/stage_2.dart';
import 'package:pack_verity/views/final_assembly/stage_3.dart';
import 'package:pack_verity/views/final_assembly/udi_scan.dart';
import 'package:pack_verity/views/hh_box/add.dart';
import 'package:pack_verity/views/history/history.dart';
import 'package:pack_verity/views/settings/settings.dart';
import 'package:pack_verity/views/verify_component/verify_component.dart';

class FinalAssembly extends StatelessWidget {
  const FinalAssembly({super.key});

  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    final screenHeight = MediaQuery.of(context).size.height;

    return Scaffold(
      appBar: AppBar(
        elevation: 0.0,
        systemOverlayStyle: const SystemUiOverlayStyle(
          statusBarColor: colorInputField,
          statusBarIconBrightness: Brightness.light, // For Android (dark icons)
          statusBarBrightness: Brightness.light, // For iOS (dark icons)
        ),
        backgroundColor: colorInputField,
        centerTitle: true,
        iconTheme: const IconThemeData(
          color: Colors.white, //change your color here
        ),
        title: Text(
          'Final Assembly',
          style:
              const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
        ),
      ),
      body: Center(
        child: SingleChildScrollView(
          child: SizedBox(
            height: screenHeight,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Expanded(
                  flex: 5,
                  child: Padding(
                    padding: const EdgeInsets.all(15.0),
                    child: Column(
                      children: [
                        const SizedBox(
                          height: 40,
                        ),
                        Row(
                          children: [
                            Expanded(
                              child: HomeOperationsButton(
                                  data: HomeOperationsButtonData(
                                      const Color.fromARGB(255, 218, 206, 156),
                                      const Color.fromARGB(255, 197, 172, 7),
                                      "Stage 1",
                                      "Configure category for the device",
                                      const Icon(
                                        Icons.looks_one,
                                        size: 60,
                                        color: Color.fromARGB(255, 197, 172, 7),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const StageOnePage()),
                                            )
                                          })),
                            ),
                            Expanded(
                              child: HomeOperationsButton(
                                  data: HomeOperationsButtonData(
                                      const Color.fromARGB(255, 218, 206, 156),
                                      const Color.fromARGB(255, 197, 172, 7),
                                      "Stage 2",
                                      "Configure bladder, UPL for the device",
                                      const Icon(
                                        Icons.looks_two,
                                        size: 60,
                                        color:
                                            Color.fromARGB(255, 35, 169, 193),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const StageTwoPage()),
                                            )
                                          })),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            Expanded(
                              child: HomeOperationsButton(
                                  data: HomeOperationsButtonData(
                                      const Color.fromARGB(255, 218, 206, 156),
                                      const Color.fromARGB(255, 197, 172, 7),
                                      "Stage 3",
                                      "Configure UDI, adapter for the device",
                                      const Icon(
                                        Icons.looks_3,
                                        size: 60,
                                        color:
                                            Color.fromARGB(255, 107, 23, 180),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const StageThreePage()),
                                            )
                                          })),
                            ),
                            Expanded(
                              child: HomeOperationsButton(
                                  data: HomeOperationsButtonData(
                                      const Color.fromARGB(255, 218, 206, 156),
                                      const Color.fromARGB(255, 197, 172, 7),
                                      "Cartoon Box",
                                      "Create cartoon box for the devices",
                                      const Icon(
                                        Icons.archive,
                                        size: 60,
                                        color:
                                            Color.fromARGB(255, 149, 185, 102),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const CartoonBoxPage()),
                                            )
                                          })),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            Expanded(
                              child: HomeOperationsButton(
                                  data: HomeOperationsButtonData(
                                      const Color.fromARGB(255, 218, 206, 156),
                                      const Color.fromARGB(255, 197, 172, 7),
                                      "UDI Scan",
                                      "Scan and see details of the device",
                                      const Icon(
                                        Icons.search,
                                        size: 60,
                                        color: Color.fromARGB(255, 0, 0, 0),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const UDIScanPage()),
                                            )
                                          })),
                            ),
                            Expanded(
                              child: SizedBox.shrink(),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
