import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/auth_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/common_components/common_popups.dart';
import 'package:pack_verity/views/common_components/text_input.dart';

class ResetPasswordPage extends StatefulWidget {
  const ResetPasswordPage({Key? key}) : super(key: key);

  @override
  State<ResetPasswordPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<ResetPasswordPage> {
  final _formKey = GlobalKey<FormState>();
  final repeatPasswordController = TextEditingController();
  final passwordController = TextEditingController();
  bool signInClicked = false;

  @override
  void dispose() {
    repeatPasswordController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  Future<void> _resetPassword() async {
    if (_formKey.currentState?.validate() ?? false) {
      setState(() => signInClicked = true);

      try {
        final result =
            await sl.get<AuthService>().resetPassword(passwordController.text);

        if (!mounted) return;

        if (result != null && result.isSuccess()) {
          handlePasswordResetSuccess(
              context, "Password reset successful. Please login");
        } else {
          handleError(context, "Something went wrong. Please try again");
        }
      } catch (_) {
        handleError(context, "Something went wrong. Please try again");
      } finally {
        setState(() => signInClicked = false);
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
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
      ),
      body: Column(
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
                    offset: const Offset(0, 0.5), // changes position of shadow
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
              child: Form(
                  key: _formKey,
                  child: Padding(
                    padding: const EdgeInsets.all(30.0),
                    child: SingleChildScrollView(
                      child: Column(
                        children: [
                          const Text(
                            "Create new password",
                            style: TextStyle(
                                fontSize: 20,
                                color: Colors.grey,
                                fontWeight: FontWeight.bold),
                          ),
                          const SizedBox(
                            height: 20,
                          ),
                          TextInputCustom(
                              data: TestInputData(
                                  obscureText: true,
                                  controller: passwordController,
                                  validatorFun: (val) {
                                    if (val!.isEmpty) {
                                      return "Password cannot be empty";
                                    } else if (val.length < 6) {
                                      return "Password must be at least 6 characters";
                                    } else if (repeatPasswordController
                                            .text.isNotEmpty &&
                                        val != repeatPasswordController.text) {
                                      return "Password does not match";
                                    } else {
                                      null;
                                    }
                                  },
                                  labelText: "Password")),
                          const SizedBox(
                            height: 10,
                          ),
                          TextInputCustom(
                              data: TestInputData(
                                  controller: repeatPasswordController,
                                  obscureText: true,
                                  validatorFun: (val) {
                                    if (val!.isEmpty) {
                                      return "Retype password cannot be empty";
                                    } else if (val.length < 6) {
                                      return "Password must be at least 6 characters";
                                    } else if (passwordController
                                            .text.isNotEmpty &&
                                        passwordController.text != val) {
                                      return "Password does not match";
                                    } else {
                                      null;
                                    }
                                  },
                                  labelText: "Repeat Password")),
                          const SizedBox(
                            height: 20,
                          ),
                          Row(
                            children: [
                              Expanded(
                                child: SizedBox(
                                  height: 60,
                                  child: CupertinoButton(
                                    color: colorInputField,
                                    onPressed: _resetPassword,
                                    child: signInClicked
                                        ? const SpinKitWave(
                                            color: Colors.white,
                                            size: 20.0,
                                          )
                                        : const Text("Create new password",
                                            style: TextStyle(
                                                color: Colors.white,
                                                fontSize: 16)),
                                  ),
                                ),
                              ),
                            ],
                          ),
                          const SizedBox(
                            height: 8,
                          ),
                          const Text(
                            "Didn't have a account. Contact administrator",
                            style: TextStyle(fontSize: 12, color: Colors.black),
                          ),
                        ],
                      ),
                    ),
                  )))
        ],
      ),
    );
  }
}
