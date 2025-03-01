import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/auth_service.dart';
import 'package:pack_verity/utils/colors.dart';
import 'package:pack_verity/utils/keyBox.dart';
import 'package:pack_verity/views/auth/reset_password/reset_password.dart';
import 'package:pack_verity/views/common_components/common_popups.dart';
import 'package:pack_verity/views/common_components/text_input.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();
  bool signInClicked = false;

  @override
  void dispose() {
    usernameController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  Future<void> _login() async {
    if (_formKey.currentState?.validate() ?? false) {
      setState(() => signInClicked = true);

      try {
        final result = await sl
            .get<AuthService>()
            .login(usernameController.text, passwordController.text);

        if (!mounted) return;

        if (result != null && result.isSuccess()) {
          Navigator.pushReplacementNamed(context, '/');
        } else if (result?.status == "E3000") {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const ResetPasswordPage()),
          );
        } else {
          handleError(context, "Username or Password Invalid");
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
                            "Login",
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
                                  controller: usernameController,
                                  validatorFun: (val) {
                                    if (val!.isEmpty) {
                                      return "Username cannot be empty";
                                    } else {
                                      null;
                                    }
                                  },
                                  labelText: "Username")),
                          const SizedBox(
                            height: 10,
                          ),
                          TextInputCustom(
                              data: TestInputData(
                                  obscureText: true,
                                  controller: passwordController,
                                  validatorFun: (val) {
                                    if (val!.isEmpty) {
                                      return "Password cannot be empty";
                                    } else {
                                      null;
                                    }
                                  },
                                  labelText: "Password")),
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
                                    onPressed: _login,
                                    child: signInClicked
                                        ? const SpinKitWave(
                                            color: Colors.white,
                                            size: 20.0,
                                          )
                                        : const Text("Sign In"),
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
