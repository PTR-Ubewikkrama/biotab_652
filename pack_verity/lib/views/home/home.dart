import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/auth_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/auth/login/login.dart';
import 'package:pack_verity/views/common_components/home_button_card.dart';
import 'package:pack_verity/views/final_assembly/fa.dart';
import 'package:pack_verity/views/hh_box/add.dart';
import 'package:pack_verity/views/history/history.dart';
import 'package:pack_verity/views/settings/settings.dart';
import 'package:pack_verity/views/verify_component/verify_component.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

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
        actions: [
          IconButton(
            icon: const Icon(
              Icons.logout,
              color: Colors.white,
              weight: 10,
            ),
            onPressed: () {
              sl.get<AuthService>().logout().then((value) {
                Navigator.of(context, rootNavigator: true).pushAndRemoveUntil(
                  CupertinoPageRoute(
                    builder: (BuildContext context) {
                      return const LoginPage();
                    },
                  ),
                  (_) => false,
                );
              });
            },
          ),
        ],
      ),
      body: Center(
        child: SingleChildScrollView(
          child: SizedBox(
            height: screenHeight,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Expanded(
                  flex: 2,
                  child: Container(
                    decoration: BoxDecoration(
                      color: colorInputField,
                      borderRadius: const BorderRadius.only(
                          bottomLeft: Radius.circular(60),
                          bottomRight: Radius.circular(0)),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.grey.withOpacity(0.5),
                          spreadRadius: 6,
                          blurRadius: 10,
                          offset: const Offset(
                              0, 0.5), // changes position of shadow
                        ),
                      ],
                    ),
                    width: screenWidth,
                    height: screenHeight / 4,
                    child: const Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          SizedBox(
                            height: 40,
                          ),
                          Text(
                            projectName,
                            style: TextStyle(
                                fontSize: 35,
                                fontWeight: FontWeight.bold,
                                color: Colors.white),
                          ),
                        ]),
                  ),
                ),
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
                                      "Device History",
                                      "Scan device and check details",
                                      const Icon(
                                        Icons.history,
                                        size: 60,
                                        color: Color.fromARGB(255, 197, 172, 7),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const HistoryPage()),
                                            )
                                          })),
                            ),
                            Expanded(
                              child: HomeOperationsButton(
                                  data: HomeOperationsButtonData(
                                      const Color.fromARGB(255, 218, 206, 156),
                                      const Color.fromARGB(255, 197, 172, 7),
                                      "BT Device",
                                      "Add a new BT Device",
                                      const Icon(
                                        Icons.add_box,
                                        size: 60,
                                        color:
                                            Color.fromARGB(255, 35, 169, 193),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const AddBTDevicePage()),
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
                                      "Verify Component",
                                      "Scan any and check status",
                                      const Icon(
                                        Icons.search,
                                        size: 60,
                                        color:
                                            Color.fromARGB(255, 107, 23, 180),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const VerifyComponentPage()),
                                            )
                                          })),
                            ),
                            // Expanded(
                            //   child: HomeOperationsButton(
                            //       data: HomeOperationsButtonData(
                            //           const Color.fromARGB(255, 218, 206, 156),
                            //           const Color.fromARGB(255, 197, 172, 7),
                            //           "Final Assembly",
                            //           "FA operations",
                            //           const Icon(
                            //             Icons.precision_manufacturing,
                            //             size: 60,
                            //             color:
                            //                 Color.fromARGB(255, 149, 185, 102),
                            //           ),
                            //           () => {
                            //                 Navigator.push(
                            //                   context,
                            //                   MaterialPageRoute(
                            //                       builder: (context) =>
                            //                           const FinalAssembly()),
                            //                 )
                            //               })),
                            // ),
                            Expanded(
                              child: HomeOperationsButton(
                                  data: HomeOperationsButtonData(
                                      const Color.fromARGB(255, 218, 206, 156),
                                      const Color.fromARGB(255, 197, 172, 7),
                                      "Settings",
                                      "Set camera view point settings",
                                      const Icon(
                                        Icons.settings,
                                        size: 60,
                                        color: Color.fromARGB(255, 0, 0, 0),
                                      ),
                                      () => {
                                            Navigator.push(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      const SettingsView()),
                                            )
                                          })),
                            ),
                          ],
                        ),
                        Row(
                          children: [
                            // Expanded(
                            //   child: HomeOperationsButton(
                            //       data: HomeOperationsButtonData(
                            //           const Color.fromARGB(255, 218, 206, 156),
                            //           const Color.fromARGB(255, 197, 172, 7),
                            //           "Settings",
                            //           "Set camera view point settings",
                            //           const Icon(
                            //             Icons.settings,
                            //             size: 60,
                            //             color: Color.fromARGB(255, 0, 0, 0),
                            //           ),
                            //           () => {
                            //                 Navigator.push(
                            //                   context,
                            //                   MaterialPageRoute(
                            //                       builder: (context) =>
                            //                           const SettingsView()),
                            //                 )
                            //               })),
                            // ),
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
