import 'package:flutter/material.dart';

class TestInputData {
  final TextEditingController controller;
  final String? Function(String?)? validatorFun;
  final Future<String?> Function(String?)? asyncValidatorFun;
  final void Function(String)? onComplete;
  final String labelText;
  final TextInputAction? textInputAction;
  final bool obscureText;
  final TextInputType inputType;
  final int maxLines;
  final bool enabled;

  TestInputData(
      {required this.controller,
      required this.validatorFun,
      this.asyncValidatorFun,
      required this.labelText,
      this.onComplete,
      this.textInputAction = TextInputAction.next,
      this.obscureText = false,
      this.inputType = TextInputType.text,
      this.maxLines = 1,
      this.enabled = true});
}

class TextInputCustom extends StatefulWidget {
  final TestInputData data;
  const TextInputCustom({Key? key, required this.data}) : super(key: key);

  @override
  State<TextInputCustom> createState() => _TextInputCustomState(data);
}

class _TextInputCustomState extends State<TextInputCustom> {
  late TestInputData data;
  bool isPasswordVisible = false;
  String? validationError;
  bool isValidating = false;

  _TextInputCustomState(this.data) {
    isPasswordVisible = data.obscureText;
  }

  Future<void> _validateAsync(String? value) async {
    if (data.asyncValidatorFun != null) {
      setState(() {
        isValidating = true;
      });
      String? error = await data.asyncValidatorFun!(value);
      setState(() {
        validationError = error;
        isValidating = false;
      });
    }
  }

  @override
  void initState() {
    super.initState();
    data.controller.addListener(() {
      final text = data.controller.text;
      if (data.asyncValidatorFun != null) {
        _validateAsync(text);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Theme(
      data: Theme.of(context),
      child: TextFormField(
        enabled: data.enabled,
        maxLines: data.maxLines,
        style: const TextStyle(color: Colors.black),
        autovalidateMode: AutovalidateMode.onUnfocus,
        textInputAction: data.textInputAction ?? TextInputAction.next,
        controller: data.controller,
        validator: (val) {
          if (data.validatorFun != null) {
            return data.validatorFun!(val);
          }
          if (validationError != null) {
            return validationError;
          }
          return null;
        },
        obscureText: isPasswordVisible,
        keyboardType: data.inputType,
        onChanged: (val) {
          if (data.asyncValidatorFun != null) {
            _validateAsync(val);
          }
        },
        onEditingComplete: () {
          if (data.onComplete != null) {
            data.onComplete!(data.controller.text);
          }
        },
        decoration: InputDecoration(
          labelText: data.labelText,
          suffixIcon: data.obscureText
              ? IconButton(
                  icon: Icon(
                    !isPasswordVisible && data.obscureText
                        ? Icons.visibility
                        : Icons.visibility_off,
                    color: Colors.grey,
                  ),
                  onPressed: () {
                    setState(() {
                      isPasswordVisible = !isPasswordVisible;
                    });
                  },
                )
              : isValidating
                  ? const CircularProgressIndicator()
                  : null,
          errorText: validationError,
        ),
      ),
    );
  }
}
