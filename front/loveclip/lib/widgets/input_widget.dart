import 'package:flutter/material.dart';

class InputWidget extends StatelessWidget {
  final TextEditingController idController;
  final String text;
  final Color bgColor;
  final bool obscure;

  const InputWidget({
    super.key,
    required this.idController,
    required this.text,
    required this.bgColor,
    required this.obscure,
  }) : _idController = idController;

  final TextEditingController _idController;

  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: _idController,
      obscureText: obscure,
      decoration: InputDecoration(
        filled: true,
        fillColor: bgColor,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(10), // 둥근 테두리 설정
          borderSide: BorderSide.none,
        ),
        hintText: text, // hintText: placeholder 역할
        contentPadding:
            const EdgeInsets.symmetric(horizontal: 30), // padding 수정
      ),
    );
  }
}
