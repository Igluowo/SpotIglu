package com.example.spotiglu

import android.content.Context
import android.media.MediaMetadataRetriever
import com.example.spotiglu.viewModel.obtenerRuta

fun cancionLista() : List<Cancion> {
    var lista = listOf<Cancion>(
       Cancion(0, "Demolition Lovers", R.raw.mychemicalromance_demolitionlovers, "I Brought You my Bullets, You Brought me Your Love",
    "My Chemical Romance", R.drawable.i_brought_you_my_bullets__you_brought_me_your_love),
        Cancion(1, "Bailaré", R.raw.cyclo_bailare, "Bailaré", "Cyclo", R.drawable.bailare),
        Cancion(2, "Maquiavélico", R.raw.canserbero_maquiavelico, "Muerte", "Canserbero", R.drawable.muerte),
        Cancion(3, "Si preguntan x mi", R.raw.cyclo_sipreguntanxmi, "Si preguntan X mi", "Cyclo", R.drawable.sipreguntanxmi),
        Cancion(4, "Noches Largas", R.raw.serbia_nocheslargas, "Melodramática", "SERBIA, Bruses", R.drawable.melodramatica)
    )
    return lista
}

fun iBroughtCanciones(): List<Cancion> {
    val album = "I Brought You my Bullets, You Brought me Your Love"
    val imagen = R.drawable.i_brought_you_my_bullets__you_brought_me_your_love
    val autor = "My Chemical Romance"
    var lista = listOf<Cancion>(
        Cancion(0, "Romance", R.raw.romance_mychemicalromance, album, autor, imagen),
        Cancion(1, "Demolition Lovers", R.raw.mychemicalromance_demolitionlovers, album, autor, imagen),
        Cancion(2, "Cubicles", R.raw.cubicles_mychemicalromance, album, autor, imagen),
        Cancion(3, "Drowning Lessons", R.raw.drowninglessons_mychemicalromance, album, autor, imagen),
        Cancion(4, "Early Sunsets Over Monroeville", R.raw.earlysunsetsovermonroeville_mychemicalromance, album, autor, imagen),
        Cancion(5, "HeadFirsts for Halos", R.raw.headfirstforhalos_mychemicalromance, album, autor, imagen),
        Cancion(6, "Our Lady of Sorrows", R.raw.ourladyofsorrows_mychemicalromance, album, autor, imagen),
        Cancion(7, "Skylines and Turnstiles", R.raw.skylinesandturnstiles_mychemicalromance, album, autor, imagen),
        Cancion(8, "Vampires Will Never Hurts You", R.raw.vampireswillneverhurtyou_mychemicalromance, album, autor, imagen),
        Cancion(9, "Honey, This Mirror Isn't Big Enough for the Two of Us", R.raw.honeythismirrorisntbigenoughforthetwoofus_mychemicalromance, album, autor, imagen),
        Cancion(10, "This is the Best Day Ever", R.raw.thisisthebestdayever_mychemicalromance, album, autor, imagen)
    )
    return lista
}

fun muerteCanciones(): List<Cancion> {
    val album = "Muerte"
    val autor = "Canserbero"
    val imagen = R.drawable.muerte
    var lista = listOf<Cancion>(
        Cancion(0, "Ces't la Mort", R.raw.canserbero_cestlamort, album, autor, imagen),
        Cancion(1, "De mi Muerte", R.raw.canserbero_demimuerte, album, autor, imagen),
        Cancion(2, "Maquiavélico", R.raw.canserbero_maquiavelico, album, autor, imagen),
        Cancion(3, "El Primer Trago", R.raw.canserbero_elprimertrago, album, autor, imagen),
        Cancion(4, "En el Valle de las Sombras", R.raw.canserbero_enelvalledelassombras, album, autor, imagen),
        Cancion(5, "Es Épico", R.raw.canserbero_esepico, album, autor, imagen),
        Cancion(6, "Jeremías 17:5", R.raw.canserbero_jeremias17_5, album, autor, imagen),
        Cancion(7, "La Hora del Juicio", R.raw.canserbero_lahoradeljuicio, album, autor, imagen),
        Cancion(8, "Llovía", R.raw.canserbero_llovia, album, autor, imagen),
        Cancion(9, "Mundo de Piedra", R.raw.canserbero_mundodepiedra, album, autor, imagen),
        Cancion(10, "Ser Vero", R.raw.canserbero_servero, album, autor, imagen),
        Cancion(11, "Sin Mercy", R.raw.canserbero_sinmercy, album, autor, imagen),
        Cancion(12, "Un Dia en el Barrio", R.raw.canserbero_undiaenelbarrio, album, autor, imagen),
        Cancion(13, "Y en un Espejo Vi", R.raw.canserbero_yenunespejovi, album, autor, imagen)
    )
    return lista
}

fun supremaCanciones(): List<Cancion> {
    val album = "Suprema Alabanza"
    val autor = "Elemento"
    val imagen = R.drawable.suprema
    var lista = listOf<Cancion>(
        Cancion(0, "Como no Decirte", R.raw.elemento_comonodecirte, album, autor, imagen),
        Cancion(1, "Confiando en Tu Gracia", R.raw.elemento_confiandoentugracia, album, autor, imagen),
        Cancion(2, "No me Hables de Locuras", R.raw.elemento_nomehablesdelocuras, album, autor, imagen),
        Cancion(3, "Sin tu Ayuda", R.raw.elemento_sintuayuda, album, autor, imagen),
        Cancion(4, "Uno mas Grande", R.raw.elemento_unomasgrande, album, autor, imagen),
        )
    return lista
}

fun listaTodas(): List<Cancion> {
    val album = "I Brought You my Bullets, You Brought me Your Love"
    val imagen = R.drawable.i_brought_you_my_bullets__you_brought_me_your_love
    val autor = "My Chemical Romance"
    val album2 = "Muerte"
    val autor2 = "Canserbero"
    val imagen2 = R.drawable.muerte
    val album3 = "Suprema Alabanza"
    val autor3 = "Elemento"
    val imagen3 = R.drawable.suprema
    var lista = listOf<Cancion>(
        Cancion(0, "Romance", R.raw.romance_mychemicalromance, album, autor, imagen),
        Cancion(1, "Demolition Lovers", R.raw.mychemicalromance_demolitionlovers, album, autor, imagen),
        Cancion(2, "Cubicles", R.raw.cubicles_mychemicalromance, album, autor, imagen),
        Cancion(3, "Drowning Lessons", R.raw.drowninglessons_mychemicalromance, album, autor, imagen),
        Cancion(4, "Early Sunsets Over Monroeville", R.raw.earlysunsetsovermonroeville_mychemicalromance, album, autor, imagen),
        Cancion(5, "HeadFirsts for Halos", R.raw.headfirstforhalos_mychemicalromance, album, autor, imagen),
        Cancion(6, "Our Lady of Sorrows", R.raw.ourladyofsorrows_mychemicalromance, album, autor, imagen),
        Cancion(7, "Skylines and Turnstiles", R.raw.skylinesandturnstiles_mychemicalromance, album, autor, imagen),
        Cancion(8, "Vampires Will Never Hurts You", R.raw.vampireswillneverhurtyou_mychemicalromance, album, autor, imagen),
        Cancion(9, "Honey, This Mirror Isn't Big Enough for the Two of Us", R.raw.honeythismirrorisntbigenoughforthetwoofus_mychemicalromance, album, autor, imagen),
        Cancion(10, "This is the Best Day Ever", R.raw.thisisthebestdayever_mychemicalromance, album, autor, imagen),
        Cancion(11, "Bailaré", R.raw.cyclo_bailare, "Bailaré", "Cyclo", R.drawable.bailare),
        Cancion(12, "Si preguntan x mi", R.raw.cyclo_sipreguntanxmi, "Si preguntan X mi", "Cyclo", R.drawable.sipreguntanxmi),
        Cancion(13, "Noches Largas", R.raw.serbia_nocheslargas, "Melodramática", "SERBIA, Bruses", R.drawable.melodramatica),
        Cancion(14, "Ces't la Mort", R.raw.canserbero_cestlamort, album2, autor2, imagen2),
        Cancion(15, "De mi Muerte", R.raw.canserbero_demimuerte, album2, autor2, imagen2),
        Cancion(16, "Maquiavélico", R.raw.canserbero_maquiavelico, album2, autor2, imagen2),
        Cancion(17, "El Primer Trago", R.raw.canserbero_elprimertrago, album2, autor2, imagen2),
        Cancion(18, "En el Valle de las Sombras", R.raw.canserbero_enelvalledelassombras, album2, autor2, imagen2),
        Cancion(19, "Es Épico", R.raw.canserbero_esepico, album2, autor2, imagen2),
        Cancion(20, "Jeremías 17:5", R.raw.canserbero_jeremias17_5, album2, autor2, imagen2),
        Cancion(21, "La Hora del Juicio", R.raw.canserbero_lahoradeljuicio, album2, autor2, imagen2),
        Cancion(22, "Llovía", R.raw.canserbero_llovia, album2, autor2, imagen2),
        Cancion(23, "Mundo de Piedra", R.raw.canserbero_mundodepiedra, album2, autor2, imagen2),
        Cancion(24, "Ser Vero", R.raw.canserbero_servero, album2, autor2, imagen2),
        Cancion(25, "Sin Mercy", R.raw.canserbero_sinmercy, album2, autor2, imagen2),
        Cancion(26, "Un Dia en el Barrio", R.raw.canserbero_undiaenelbarrio, album2, autor2, imagen2),
        Cancion(27, "Y en un Espejo Vi", R.raw.canserbero_yenunespejovi, album2, autor2, imagen2),
        Cancion(28, "Como no Decirte", R.raw.elemento_comonodecirte, album3, autor3, imagen3),
        Cancion(29, "Confiando en Tu Gracia", R.raw.elemento_confiandoentugracia, album3, autor3, imagen3),
        Cancion(30, "No me Hables de Locuras", R.raw.elemento_nomehablesdelocuras, album3, autor3, imagen3),
        Cancion(31, "Sin tu Ayuda", R.raw.elemento_sintuayuda, album3, autor3, imagen3),
        Cancion(32, "Uno mas Grande", R.raw.elemento_unomasgrande, album3, autor3, imagen3)
    )
    return lista
}

fun listaRap() : List<Cancion> {
    val album2 = "Muerte"
    val autor2 = "Canserbero"
    val imagen2 = R.drawable.muerte
    val album3 = "Suprema Alabanza"
    val autor3 = "Elemento"
    val imagen3 = R.drawable.suprema
    val lista = listOf<Cancion>(
        Cancion(0, "Bailaré", R.raw.cyclo_bailare, "Bailaré", "Cyclo", R.drawable.bailare),
        Cancion(1, "Si preguntan x mi", R.raw.cyclo_sipreguntanxmi, "Si preguntan X mi", "Cyclo", R.drawable.sipreguntanxmi),
        Cancion(2, "Ces't la Mort", R.raw.canserbero_cestlamort, album2, autor2, imagen2),
        Cancion(3, "De mi Muerte", R.raw.canserbero_demimuerte, album2, autor2, imagen2),
        Cancion(4, "Maquiavélico", R.raw.canserbero_maquiavelico, album2, autor2, imagen2),
        Cancion(5, "El Primer Trago", R.raw.canserbero_elprimertrago, album2, autor2, imagen2),
        Cancion(6, "En el Valle de las Sombras", R.raw.canserbero_enelvalledelassombras, album2, autor2, imagen2),
        Cancion(7, "Es Épico", R.raw.canserbero_esepico, album2, autor2, imagen2),
        Cancion(8, "Jeremías 17:5", R.raw.canserbero_jeremias17_5, album2, autor2, imagen2),
        Cancion(9, "La Hora del Juicio", R.raw.canserbero_lahoradeljuicio, album2, autor2, imagen2),
        Cancion(10, "Llovía", R.raw.canserbero_llovia, album2, autor2, imagen2),
        Cancion(11, "Mundo de Piedra", R.raw.canserbero_mundodepiedra, album2, autor2, imagen2),
        Cancion(12, "Ser Vero", R.raw.canserbero_servero, album2, autor2, imagen2),
        Cancion(13, "Sin Mercy", R.raw.canserbero_sinmercy, album2, autor2, imagen2),
        Cancion(14, "Un Dia en el Barrio", R.raw.canserbero_undiaenelbarrio, album2, autor2, imagen2),
        Cancion(15, "Y en un Espejo Vi", R.raw.canserbero_yenunespejovi, album2, autor2, imagen2),
        Cancion(16, "Como no Decirte", R.raw.elemento_comonodecirte, album3, autor3, imagen3),
        Cancion(17, "Confiando en Tu Gracia", R.raw.elemento_confiandoentugracia, album3, autor3, imagen3),
        Cancion(18, "No me Hables de Locuras", R.raw.elemento_nomehablesdelocuras, album3, autor3, imagen3),
        Cancion(19, "Sin tu Ayuda", R.raw.elemento_sintuayuda, album3, autor3, imagen3),
        Cancion(20, "Uno mas Grande", R.raw.elemento_unomasgrande, album3, autor3, imagen3)
    )

    return lista
}

fun albumes() : ArrayList<Lista> {
    var lista = ArrayList<Lista>()
    var lista1 = Lista(0, "I Brought You my Bullets You Brought me Your Love", "My Chemical Romance",
        "Album", R.drawable.i_brought_you_my_bullets__you_brought_me_your_love, iBroughtCanciones(), 2002)
    var lista2 = Lista(1,"Muerte", "Canserbero", "Album", R.drawable.muerte, muerteCanciones(), 2012)
    var lista3 = Lista(2, "Suprema Alabanza", "Elemento", "Album", R.drawable.suprema, supremaCanciones(), 2012)
    lista.add(lista1)
    lista.add(lista2)
    lista.add(lista3)
    return lista
}

fun playlist() : ArrayList<Lista> {
    var lista = ArrayList<Lista>()
    var lista1 = Lista(0, "Canciones que te gustan", "", "Lista", R.drawable.corazon,
        listaTodas(), 0)
    var lista2 = Lista(1, "Rap en Español", "", "Lista", R.drawable.rap, listaRap(),
        0)
    lista.add(lista1)
    lista.add(lista2)
    return lista
}