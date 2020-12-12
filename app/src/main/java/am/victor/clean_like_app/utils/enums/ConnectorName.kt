package am.victor.clean_like_app.utils.enums

enum class ConnectorName(val model: String) {
    Tesla("Tesla"),
    Chademo("(DC) Chademo"),
    Combo("(DC) CCS/SAE Combo"),
    Standart("(AC) J-1772 (Standard)")
}