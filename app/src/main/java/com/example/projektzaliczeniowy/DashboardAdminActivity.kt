package com.example.projektzaliczeniowy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projektzaliczeniowy.databinding.ActivityDashboardAdminBinding
import com.example.projektzaliczeniowy.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding: ActivityDashboardUserBinding

    // firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    // Lista książek
    private val bookList = listOf(
        Book("Harry Potter i Kamień Filozoficzny", R.drawable.harry_potter1, "„Harry Potter i Kamień Filozoficzny” to pierwsza część kultowej serii autorstwa J.K. Rowling. Książka opowiada historię młodego chłopca, Harry'ego Pottera, który dowiaduje się, że jest czarodziejem i zostaje przyjęty do magicznej szkoły – Hogwartu. Tam poznaje nowych przyjaciół – Hermionę Granger i Ronalda Weasleya – oraz odkrywa tajemnicze wydarzenia związane z Kamieniem Filozoficznym, potężnym artefaktem, który może dać nieśmiertelność. Harry staje do walki z siłami zła, które chcą zdobyć Kamień, a jednocześnie odkrywa prawdę o swojej przeszłości i swoim związku z najpotężniejszym czarnoksiężnikiem – Voldemortem.\n" +
                "\n" +
                "To książka pełna magii, przygód i niezwykłych postaci, która wprowadza czytelników do niezwykłego świata czarodziejów, gdzie dobro i zło nieustannie ze sobą walczą.\n"),
        Book("Harry Potter i Komnata Tajemnic", R.drawable.harry_potter2, "„Harry Potter i Komnata Tajemnic” to druga część serii J.K. Rowling, w której Harry wraca do Hogwartu na drugi rok nauki. Wkrótce po powrocie odkrywa, że w szkole dzieją się dziwne i przerażające wydarzenia – uczniowie zostają zamieniani w kamień, a tajemnicze napisy pojawiają się na ścianach. Legendy głoszą, że w Hogwarcie istnieje Komnata Tajemnic, która zawiera mroczne sekrety i niebezpieczne potwory.\n" +
                "\n" +
                "Harry, Hermiona i Ron postanawiają odkryć prawdę i stanąć w obronie swojej szkoły przed niewidzialnym zagrożeniem. Podczas tej przygody Harry zmierzy się z nowymi wyzwaniami, dowie się więcej o swojej przeszłości i spotka nowego wroga – dziedzica Salazara Slytherina, który pragnie zniszczyć wszystkich „czarodziejów niemagicznych”.\n" +
                "\n" +
                "To pełna napięcia, tajemnic i magii książka, która jeszcze bardziej rozwija świat czarodziejów i wprowadza nowych, fascynujących bohaterów.\n"),
        Book("Harry Potter i Więzień Azkabanu", R.drawable.harry_potter3, "„Harry Potter i Więzień Azkabanu” to trzecia część serii J.K. Rowling, która kontynuuje przygody Harry'ego Pottera w jego trzecim roku nauki w Hogwarcie. Tym razem Harry dowiaduje się, że grozi mu niebezpieczeństwo ze strony niebezpiecznego więźnia, Syriusza Blacka, który uciekł z Azkabanu, czarodziejskiego więzienia. Black jest podejrzewany o zdradę, która doprowadziła do śmierci rodziców Harry'ego.\n" +
                "\n" +
                "W miarę jak Harry zgłębia tajemnice związane z Syriuszem Blackiem, odkrywa szokującą prawdę o przeszłości swojej rodziny. Na swojej drodze spotyka nowe, magiczne stworzenia, jak hipogryfy, oraz nowych nauczycieli, w tym Remusa Lupina, który ma swoje sekrety. Harry, Ron i Hermiona stają przed wyzwaniem, by rozwiązać zagadkę więźnia i stawić czoła nie tylko zagrożeniom z przeszłości, ale i potężnym siłom ciemności.\n" +
                "\n" +
                "Książka pełna emocji, przygód i magii, odsłania kolejne tajemnice świata czarodziejów, wprowadzając bardziej złożoną fabułę i rozwijając wątki dotyczące lojalności, przyjaźni i poświęcenia.\n"),
        Book("Harry Potter i Czara Ognia", R.drawable.harry_potter4, "„Harry Potter i Czara Ognia” to czwarta część serii autorstwa J.K. Rowling. Harry wraca do Hogwartu na czwartego roku nauki, gdzie szkoła bierze udział w prestiżowym Turnieju Trójmagicznego – rywalizacji pomiędzy trzema czarodziejskimi szkołami. Harry, mimo że nie zgłaszał się do udziału, zostaje wciągnięty w turniej jako czwarty uczestnik, co wywołuje sporo kontrowersji. Podczas rywalizacji Harry stawia czoła niebezpiecznym wyzwaniom, a jego relacje z przyjaciółmi, Ronem i Hermioną, zostają wystawione na próbę.\n" +
                "\n" +
                "W miarę jak turniej się rozwija, na jaw wychodzą mroczne tajemnice. Harry odkrywa, że niebezpieczeństwo zagraża nie tylko jemu, ale i całemu światu czarodziejów. Jego starcie z siłami ciemności doprowadzi do nieoczekiwanego i tragicznego wydarzenia, które zmieni życie każdego z uczestników. Książka pełna magii, tajemnic, przyjaźni oraz rosnącego zagrożenia od najpotężniejszego czarnoksiężnika – Voldemorta.\n"),
        Book("Harry Potter i Zakon Feniksa", R.drawable.harry_potter5, "„Harry Potter i Zakon Feniksa” to piąta część serii J.K. Rowling, w której Harry staje przed jeszcze większymi wyzwaniami. Po powrocie do Hogwartu, Harry dowiaduje się, że ministerstwo magii nie wierzy w powrót Voldemorta i próbuje zdyskredytować wszystkie informacje o zagrożeniu. W obliczu rosnącego niebezpieczeństwa, Harry postanawia stworzyć Zakon Feniksa – tajną organizację, której celem jest walka z siłami ciemności.\n" +
                "\n" +
                "Równocześnie Harry zmaga się z trudnymi relacjami z przyjaciółmi i nauczycielami, a także z własnymi emocjami związanymi z przeszłością. W tej części książki pojawiają się nowe postacie, a także powraca wiele znanych, jak profesor Snape, który zaczyna odgrywać bardziej istotną rolę w życiu Harry'ego. Ostatecznie Harry odkrywa przerażającą prawdę o swoim połączeniu z Voldemortem i staje przed największym wyzwaniem swojego życia.\n" +
                "\n" +
                "To książka pełna emocji, tajemnic i trudnych wyborów, w której bohaterowie stają w obliczu mroku, a przyjaźń i lojalność są wystawione na próbę.\n"),
        Book("Harry Potter i Książę Półkrwi", R.drawable.harry_potter6, "„Harry Potter i Książę Półkrwi” to szósta część serii J.K. Rowling, w której Harry Potter wkracza w nowy, niebezpieczny etap swojej przygody. W Hogwarcie pojawia się nowy nauczyciel eliksirów, Horacy Slughorn, który zaczyna wpływać na życie uczniów, a zwłaszcza na Harry'ego, który odkrywa tajemnicę starego podręcznika eliksirów, należącego do tajemniczego „Księcia Półkrwi”. Podręcznik ten zawiera nie tylko cenne wskazówki do robienia eliksirów, ale także osobiste notatki, które pomagają Harry'emu w nauce i w walce z ciemnymi mocami.\n" +
                "\n" +
                "Jednak z każdą chwilą, świat czarodziejów staje się coraz bardziej niebezpieczny. Voldemort staje się coraz silniejszy, a Zakon Feniksa walczy ze wszystkimi jego zwolennikami. W tej książce Harry poznaje wiele mrocznych tajemnic, w tym szczegóły dotyczące przeszłości Lorda Voldemorta. Napięcie rośnie, a lojalność i przyjaźń bohaterów są wystawiane na próbę. Książka kulminuje w tragicznych wydarzeniach, które na zawsze zmienią życie Harry'ego i jego przyjaciół.\n" +
                "\n" +
                "„Harry Potter i Książę Półkrwi” to historia o dorastaniu, poświęceniu i nieuchronności przeznaczenia, w której zło zbliża się do ostatecznej konfrontacji z dobrem.\n"),
        Book("Harry Potter i Insygnia Śmierci", R.drawable.harry_potter7, "„Harry Potter i Insygnia Śmierci” to siódma i ostatnia część serii J.K. Rowling, w której Harry Potter i jego przyjaciele, Hermiona i Ron, wyruszają na niebezpieczną misję – muszą odnaleźć i zniszczyć Horcruxy, przedmioty zawierające część duszy Voldemorta, aby ostatecznie pokonać Czarnego Pana. W tej książce, Harry dowiaduje się o istnieniu tzw. Insygniów Śmierci – trzech potężnych artefaktach, które mogą odegrać kluczową rolę w walce ze złem.\n" +
                "\n" +
                "Harry, Ron i Hermiona stają w obliczu niebezpieczeństw, zdrad i strat, a ich przyjaźń jest wystawiana na ciężką próbę. Z każdym krokiem coraz bardziej zbliżają się do ostatecznego starcia z Voldemortem, które wstrząśnie całym światem czarodziejów. Książka pełna jest emocji, poświęceń, mrocznych tajemnic i bohaterstwa, a także ważnych pytań o lojalność, miłość i przeznaczenie.\n" +
                "\n" +
                "„Harry Potter i Insygnia Śmierci” to monumentalne zakończenie serii, w którym wszystkie wątki łączą się w wielką finałową bitwę o przyszłość czarodziejów, a Harry staje przed ostatecznym wyborem, który zdeterminuje losy jego świata.\n")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicjalizacja firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // Obsługa wylogowania
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Konfiguracja galerii książek
        setupGallery()
    }

    private fun checkUser() {
        // Pobierz aktualnego użytkownika
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            // Użytkownik nie jest zalogowany
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            // Użytkownik jest zalogowany, wyświetl dane użytkownika
            val email = firebaseUser.email
            binding.subTitleTv.text = email
        }
    }
    private fun setupGallery() {
        // Inicjalizacja adaptera i przekazanie kliknięcia na książkę
        val adapter = BookAdapter(bookList) { book ->
            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra("BOOK_TITLE", book.title)
            intent.putExtra("BOOK_DESCRIPTION", book.description)
            intent.putExtra("BOOK_IMAGE", book.imageResId)
            startActivity(intent)
        }

        // Ustawienie layoutu i adaptera RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 kolumny
        binding.recyclerView.adapter = adapter
    }
}