import 'package:flutter/material.dart';
import 'package:pack_verity/domain/login_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/services/auth_service.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:pack_verity/views/auth/login/login.dart';
import 'package:pack_verity/views/home/home.dart';

class LayoutPage extends StatefulWidget {
  const LayoutPage({super.key});

  @override
  State<LayoutPage> createState() => _LayoutPageState();
}

class _LayoutPageState extends State<LayoutPage> {
  @override
  build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<LoginResponse?>(
        future: sl.get<AuthService>().validateJWT(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
              child: SpinKitFadingCircle(
                color: Colors.grey,
                size: 60.0,
              ),
            );
          } else {
            if (snapshot.hasData && snapshot.data!.status == 'S1000') {
              return HomePage();
            } else if (snapshot.hasData && snapshot.data!.status == 'E2000') {
              return LoginPage();
            } else {
              return const LoginPage();
            }
          }
        },
      ),
    );
  }
}
