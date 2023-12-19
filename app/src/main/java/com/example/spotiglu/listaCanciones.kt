package com.example.spotiglu
fun cancionLista() {
    var lista = ArrayList<Cancion>()
    var cancion1 = Cancion("Demolition Lovers", R.raw.mychemicalromance_demolitionlovers, "I brought you my bullets, you brought me your love",
        "My Chemical Romance", R.drawable.i_brought_you_my_bullets__you_brought_me_your_love)
    var cancion2 = Cancion("Bailaré", R.raw.cyclo_bailare, "Bailaré", "Cyclo", R.drawable.bailare)
    var cancion3 = Cancion("Maquiavélico", R.raw.canserbero_maquiavelico, "Muerte", "Canserbero", R.drawable.muerte)
    var cancion4 = Cancion("Si preguntan x mi", R.raw.cyclo_sipreguntanxmi, "Si preguntan x mi", "Cyclo", R.drawable.sipreguntanxmi)
    var cancion5 = Cancion("Noches Largas", R.raw.serbia_nocheslargas, "Melodramática", "SERBIA, Bruses", R.drawable.melodramatica)
    lista.add(cancion1)
}