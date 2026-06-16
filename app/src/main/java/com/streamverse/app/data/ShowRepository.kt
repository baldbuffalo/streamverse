package com.streamverse.app.data

import androidx.compose.ui.graphics.Color

private fun eps(id: String, sn: Int, data: List<Pair<String, String>>): List<Episode> =
    data.mapIndexed { i, (title, desc) ->
        Episode(
            number      = i + 1,
            title       = title,
            description = desc,
            duration    = "${18 + (id[0].code * 7 + sn * 3 + i * 11) % 28} min"
        )
    }

val ALL_SHOWS = listOf(
    // ── KIDS ─────────────────────────────────────────────────────────
    Show(
        id="paw-patrol", title="PAW Patrol", category="kids", badge="🐾",
        color=Color(0xFF1A73E8), matchPercent=98, rating="TV-Y", year=2013, seasonCount=10,
        description="Ryder and his rescue dogs protect Adventure Bay from every kind of emergency — big and small.",
        tags=listOf("Animation","Adventure","Family"),
        seasons= mapOf(
            1 to eps("pp",1, listOf(
                "Pups Make Their Mark" to "The pups must save Mayor Goodway's crumbling lighthouse before it collapses.",
                "Pups Fall Festival" to "A runaway tractor threatens to completely ruin the town's beloved fall festival.",
                "Pups Save the Sea Turtles" to "Lost baby sea turtles need help finding their way back to the ocean.",
                "Pup Pup Goose" to "A confused goose family struggles to begin their winter migration south.",
                "Pups Save the Bunnies" to "Three fluffy bunnies become trapped inside a collapsed old mine shaft.",
                "Pup Pup and Away" to "Mayor Goodway drifts into the sky when her hot air balloon floats uncontrolled.",
                "Pups on Ice" to "Chickaletta falls through thin ice on the frozen bay and needs rescue.",
                "Pups and the Snow Monster" to "The legend of the Snow Monster sends all of Foggy Bottom into a panic.",
            )),
            2 to eps("pp",2, listOf(
                "Pups Save a Train" to "A runaway train must be stopped before it causes a massive catastrophe.",
                "Pups in a Fog" to "Mayor Goodway becomes stranded on Seal Island in the thick fog.",
                "Pups Save Jake's Cake" to "The team races to deliver Jake's birthday cake over the snowy mountain.",
                "Pups Save the Camping Trip" to "Ryder and the pups get lost during a fun camping trip.",
                "Pups and the Kitty-tastrophe" to "An adventurous kitten gets tangled up in a wild situation.",
                "Pups Save a Mer-Pup" to "The pups discover something magical hidden beneath the deep bay waters.",
            )),
            3 to eps("pp",3, listOf(
                "Pups in a Fix" to "Multiple emergencies break out across Adventure Bay at the same time.",
                "Pups Save a Flying Frog" to "A frog with rocket-powered legs causes chaos throughout the city.",
                "Pups in a Jam" to "The berry jam festival goes awry when the jellies get mixed up.",
                "Pups Save the Parrot" to "A clever parrot leads the PAW Patrol on a wild treasure hunt.",
                "Pups Go All Monkey" to "Escaped monkeys from the animal sanctuary cause mischief everywhere.",
            )),
        )
    ),
    Show(
        id="dora", title="Dora the Explorer", category="kids", badge="🗺️",
        color=Color(0xFFC44B1D), matchPercent=96, rating="TV-Y", year=2000, seasonCount=8,
        description="Join Dora and her monkey friend Boots on interactive adventures teaching Spanish and critical thinking.",
        tags=listOf("Animation","Educational","Adventure"),
        seasons= mapOf(
            1 to eps("dora",1, listOf(
                "The Legend of the Big Red Chicken" to "Dora and Boots set off to find the magical Big Red Chicken who grants wishes.",
                "Lost and Found" to "A baby bluebird has gotten hopelessly lost and needs help getting home.",
                "Hic-Boom-Ohhh!" to "Dora and Boots help a poor elephant who cannot stop hiccuping.",
                "Beaches" to "Dora and Boots take an exciting adventure all the way to the beach.",
                "We All Scream for Ice Cream" to "The ice cream truck has lost all its treats and Dora must help recover them.",
                "Choo-Choo!" to "The train is stuck at the station and Dora figures out how to fix it.",
            )),
            2 to eps("dora",2, listOf(
                "The Big Storm" to "A thunderstorm cuts the lights and Dora helps frightened animals find shelter.",
                "The Magic Stick" to "A magical stick Dora discovers leads to an unexpected adventure.",
                "The Missing Piece" to "Dora searches high and low to find one crucial missing puzzle piece.",
                "Rojo the Fire Truck" to "Rojo the brave fire truck is in serious trouble and Dora is ready to help.",
                "Lost Squeaky" to "Boots has lost his most beloved toy and is completely heartbroken.",
            )),
        )
    ),
    Show(
        id="bluey", title="Bluey", category="kids", badge="🐕",
        color=Color(0xFF0094C6), matchPercent=99, rating="TV-Y", year=2018, seasonCount=3,
        description="An imaginative Blue Heeler puppy finds wonder and adventure in everyday life with her family in Brisbane.",
        tags=listOf("Animation","Family","Comedy"),
        seasons= mapOf(
            1 to eps("bluey",1, listOf(
                "Bike Ride" to "Bluey learns to ride her bike with Dad's endless patience and encouragement.",
                "Taxi" to "Dad transforms into a taxi driver for Bluey and Bingo's creative game.",
                "Zoo" to "The whole Heeler family creates their own magnificent zoo at home.",
                "Hospital" to "Bluey and Bingo play hospital and nurse their stuffed animals back to health.",
                "Takeaway" to "Dad tries to collect dinner while wrangling two very energetic young pups.",
                "Dad Baby" to "Dad plays along with a bizarre but absolutely hilarious imaginative game.",
                "Markets" to "The Heeler family spends a beautiful morning at the local weekend market.",
                "Neighbours" to "Bluey makes a wonderful new friend from across the neighborhood.",
            )),
            2 to eps("bluey",2, listOf(
                "Hammerbarn" to "The whole Heeler family goes on vacation at a lovely countryside farm stay.",
                "The Pool" to "What starts as a normal pool day quickly turns into quite the adventure.",
                "Baby Race" to "Mum remembers when both Bluey and Bingo were just learning to walk.",
                "Bus Ride" to "Bluey and Bingo excitedly ride the big city bus for their very first time.",
                "Sleepytime" to "Little Bingo has a magical cosmic adventure while falling asleep.",
            )),
            3 to eps("bluey",3, listOf(
                "Perfect" to "Family trivia night becomes intensely competitive as all the Heelers go all in.",
                "Stories" to "Bluey and Bingo each tell their own very different version of a story.",
                "Tradies" to "Tradespeople come to fix the house and Bluey is utterly fascinated.",
                "Smoochy Kiss" to "Jack McKenzie comes over for a playdate that gets very competitive.",
            )),
        )
    ),
    Show(
        id="peppa-pig", title="Peppa Pig", category="kids", badge="🐷",
        color=Color(0xFFD4578A), matchPercent=97, rating="TV-Y", year=2004, seasonCount=8,
        description="Peppa is a loveable cheeky little piggy who loves muddy puddles and spending time with her family.",
        tags=listOf("Animation","Family","Preschool"),
        seasons= mapOf(
            1 to eps("peppa",1, listOf(
                "Muddy Puddles" to "Peppa and George head outside to jump in lots and lots of muddy puddles.",
                "The Playground" to "Peppa meets up with all her friends for a wonderful fun day at the playground.",
                "Best Friend" to "Peppa makes an exciting new best friend at her much-loved nursery school.",
                "Polly Parrot" to "Peppa and her family pay a visit to Granny's colorful and chatty parrot.",
                "At the Seaside" to "The whole Pig family drives to the beautiful seaside for an amazing day out.",
                "Swimming" to "Peppa and her brother George visit the local pool to begin learning to swim.",
            )),
            2 to eps("peppa",2, listOf(
                "Camping" to "The Pig family packs up and heads out on an exciting countryside camping adventure.",
                "Ballet Lesson" to "Peppa attends her very first ballet lesson and completely falls in love with it.",
                "Daddy's Movie Camera" to "Daddy Pig films the family as they make their own hilarious home movie.",
                "New Shoes" to "Everyone in the Pig family needs new shoes and heads off to the shops.",
            )),
        )
    ),
    Show(
        id="spongebob", title="SpongeBob SquarePants", category="kids", badge="🧽",
        color=Color(0xFFE8B800), matchPercent=97, rating="TV-Y7", year=1999, seasonCount=14,
        description="The absurd, silly adventures of a naive sea sponge and his dim-witted starfish best friend in Bikini Bottom.",
        tags=listOf("Animation","Comedy","Adventure"),
        seasons= mapOf(
            1 to eps("sponge",1, listOf(
                "Help Wanted / Tea at the Treedome" to "SpongeBob applies at the Krusty Krab and meets Sandy the squirrel.",
                "Bubblestand / Ripped Pants" to "SpongeBob sets up a bubble-blowing stand and embarrasses himself at the beach.",
                "Jellyfishing / Plankton!" to "Patrick and SpongeBob go jellyfishing while Plankton makes his debut.",
                "Naughty Nautical Neighbors / Boating School" to "SpongeBob repeatedly fails his boating exam at Mrs. Puff's school.",
                "Pizza Delivery / Home Sweet Pineapple" to "Squidward and SpongeBob must deliver a pizza all the way across the ocean.",
                "Sandy's Rocket / Squeaky Boots" to "SpongeBob and Patrick accidentally blast off to the moon in Sandy's rocket.",
            )),
        )
    ),
    Show(
        id="sesame-street", title="Sesame Street", category="kids", badge="🎭",
        color=Color(0xFFE8621A), matchPercent=98, rating="TV-Y", year=1969, seasonCount=53,
        description="Big Bird, Elmo, Cookie Monster and friends help children learn letters, numbers, and life skills.",
        tags=listOf("Educational","Animation","Family"),
        seasons= mapOf(
            1 to eps("sesame",1, listOf(
                "A Brand New Street" to "Welcome to Sesame Street! Meet all the wonderful residents for the first time!",
                "The Letter B" to "Big Bird and friends explore all the amazing things that start with the letter B.",
                "A Visit to the Library" to "Gordon takes the neighborhood children on a special trip to the library.",
                "Mr. Hooper's Store" to "Mr. Hooper teaches the kids what it's like to run a neighborhood store.",
                "The Number Five" to "The whole gang celebrates the number five in many fun and creative ways.",
            )),
        )
    ),
    Show(
        id="cocomelon", title="CoComelon", category="kids", badge="🎵",
        color=Color(0xFF3AB5E0), matchPercent=95, rating="TV-Y", year=2006, seasonCount=6,
        description="JJ and friends learn about the world through catchy songs, nursery rhymes, and colorful adventures.",
        tags=listOf("Animation","Musical","Preschool"),
        seasons= mapOf(
            1 to eps("coco",1, listOf(
                "Bath Song" to "JJ discovers that bath time can actually be really fun with the right attitude.",
                "Yes Yes Vegetables" to "JJ makes the surprising discovery that vegetables are actually pretty tasty!",
                "Boo Boo Song" to "JJ gets a little boo boo and learns exactly what to do to feel better.",
                "Wheels on the Bus" to "JJ and all his friends take a super fun trip on their yellow school bus.",
                "Old MacDonald" to "JJ visits Old MacDonald's farm and gets to meet all the exciting animals.",
            )),
        )
    ),
    Show(
        id="mickey-mouse", title="Mickey Mouse Clubhouse", category="kids", badge="🐭",
        color=Color(0xFFD01B24), matchPercent=96, rating="TV-Y", year=2006, seasonCount=4,
        description="Mickey Mouse and his pals use magical Mouseketools to solve problems and go on adventures.",
        tags=listOf("Animation","Educational","Family"),
        seasons= mapOf(
            1 to eps("mickey",1, listOf(
                "Mickey's Great Outdoors" to "Mickey and friends head outside for an exciting outdoor camping adventure.",
                "Minnie's Birthday" to "The whole gang secretly plans a wonderful surprise birthday party for Minnie.",
                "Donald and the Beanstalk" to "Donald and Mickey discover a magical beanstalk growing in the garden.",
                "Goofy's Bird" to "Goofy finds a very mysterious bird and tries to figure out what kind it is.",
                "Pluto's Ball" to "Pluto's favorite ball has rolled completely away and the gang helps find it.",
            )),
        )
    ),
    Show(
        id="scooby-doo", title="Scooby-Doo", category="kids", badge="🐶",
        color=Color(0xFF6A4C9C), matchPercent=94, rating="TV-Y7", year=1969, seasonCount=13,
        description="Scooby-Doo and Mystery Inc. solve spooky mysteries involving supernatural creatures across America.",
        tags=listOf("Animation","Mystery","Comedy"),
        seasons= mapOf(
            1 to eps("scooby",1, listOf(
                "What a Night for a Knight" to "The gang investigates a mysterious Black Knight haunting a museum.",
                "A Clue for Scooby Doo" to "A ghostly diver attacks boaters off the coast and the gang investigates.",
                "Hassle in the Castle" to "Mystery Inc. investigates a haunted castle on a foggy mysterious island.",
                "Mine Your Own Business" to "A ghost miner terrorizes an old gold mine and the gang must unmask him.",
                "Decoy for a Dognapper" to "A dog show is thrown into chaos by a cunning mysterious dognapper.",
                "What the Hex Going On?" to "A ghostly hex man is supposedly turning people into zombies.",
            )),
        )
    ),
    // ── ACTION ───────────────────────────────────────────────────────
    Show(
        id="breaking-bad", title="Breaking Bad", category="action", badge="⚗️",
        color=Color(0xFF2D7D46), matchPercent=99, rating="TV-MA", year=2008, seasonCount=5,
        description="A chemistry teacher turned meth manufacturer partners with a former student to build a criminal empire.",
        tags=listOf("Crime","Drama","Thriller"),
        seasons= mapOf(
            1 to eps("bb",1, listOf(
                "Pilot" to "Chemistry teacher Walter White receives shocking news and makes a life-altering decision.",
                "Cat's in the Bag" to "Walt and Jesse must deal with the dangerous aftermath of their first cook.",
                "...And the Bag's in the River" to "Walter is forced to make an excruciating moral choice on his own.",
                "Cancer Man" to "Walter's family discovers the difficult truth about his cancer diagnosis.",
                "Gray Matter" to "An unexpected offer forces Walt to confront what he truly wants.",
                "Crazy Handful of Nothin'" to "Walt sets up a dangerous meeting with Tuco, a violent drug distributor.",
                "A No-Rough-Stuff-Type Deal" to "Walt and Jesse attempt to acquire a crucial chemical ingredient.",
            )),
            2 to eps("bb",2, listOf(
                "Seven Thirty-Seven" to "A deadly new threat from Tuco forces Walt and Jesse to make drastic plans.",
                "Down" to "Jesse hits rock bottom while Walt struggles with his double life.",
                "Bit by a Dead Bee" to "Walt and Jesse must construct elaborate cover stories for their absence.",
                "Over" to "Walt makes a surprising announcement and his behavior begins to change.",
                "Better Call Saul" to "Walt and Jesse are introduced to a colorful criminal defense lawyer.",
            )),
            3 to eps("bb",3, listOf(
                "No Más" to "Walt resolves to leave the dangerous drug trade behind.",
                "Caballo Sin Nombre" to "Two mysterious assassins arrive in Albuquerque with a specific agenda.",
                "I.F.T." to "Skyler delivers a shocking ultimatum to Walter at their home.",
                "Green Light" to "Jesse attempts to cook independently with unexpected results.",
                "Más" to "Walt is offered a state-of-the-art laboratory and a lucrative deal.",
            )),
        )
    ),
    Show(
        id="stranger-things", title="Stranger Things", category="action", badge="🌀",
        color=Color(0xFF3D1A6E), matchPercent=98, rating="TV-14", year=2016, seasonCount=4,
        description="A boy's disappearance in a small Indiana town reveals a government conspiracy and a terrifying alternate dimension.",
        tags=listOf("Horror","Sci-Fi","Mystery"),
        seasons= mapOf(
            1 to eps("st",1, listOf(
                "Chapter One: The Vanishing of Will Byers" to "A young boy vanishes and strange supernatural forces emerge in Hawkins.",
                "Chapter Two: The Weirdo on Maple Street" to "A mysterious girl with psychic powers is found and taken in by Will's friends.",
                "Chapter Three: Holly, Jolly" to "Joyce receives an eerie otherworldly message from her missing son.",
                "Chapter Four: The Body" to "Authorities search for answers while the kids continue their investigation.",
                "Chapter Five: The Flea and the Acrobat" to "The boys work to understand the terrifying dimension called the Upside Down.",
                "Chapter Six: The Monster" to "Eleven reveals the truth about the creature stalking Hawkins.",
                "Chapter Seven: The Bathtub" to "Eleven makes a desperate attempt to locate both Will and Barbara.",
                "Chapter Eight: The Upside Down" to "Hopper and Joyce plunge into the Upside Down to rescue Will.",
            )),
            2 to eps("st",2, listOf(
                "Chapter One: MADMAX" to "A mysterious new girl arrives in Hawkins with a troubled hidden past.",
                "Chapter Two: Trick or Treat, Freak" to "Will experiences frightening visions from the dark Upside Down.",
                "Chapter Three: The Pollywog" to "A strange creature Will discovered begins to rapidly evolve.",
                "Chapter Four: Will the Wise" to "Will has a harrowing encounter with the terrible Shadow Monster.",
            )),
        )
    ),
    Show(
        id="the-boys", title="The Boys", category="action", badge="⚡",
        color=Color(0xFFB01A1A), matchPercent=99, rating="TV-MA", year=2019, seasonCount=4,
        description="A group of vigilantes set out to expose and take down corrupt superheroes who abuse their powers.",
        tags=listOf("Action","Satire","Superhero"),
        seasons= mapOf(
            1 to eps("boys",1, listOf(
                "The Name of the Game" to "Hughie Campbell's world is shattered and he joins a team to expose The Seven.",
                "Cherry" to "Billy Butcher recruits Hughie for a dangerous plan to target the most powerful superhero.",
                "Get Some" to "The team discovers Translucent is spying on them and must find a way to stop him.",
                "The Female of the Species" to "The boys try to smuggle a dangerous woman out of the country.",
                "Good for the Soul" to "Homelander and Starlight attend a massive Christian music festival.",
                "The Innocents" to "Butcher searches for his missing wife while Homelander's dark side is revealed.",
                "The Self-Preservation Society" to "The boys find themselves in danger from both Vought and the authorities.",
                "You Found Me" to "The most dangerous confrontation yet as the truth about Compound V is exposed.",
            )),
        )
    ),
    Show(
        id="mandalorian", title="The Mandalorian", category="action", badge="🪖",
        color=Color(0xFF5A4030), matchPercent=98, rating="TV-14", year=2019, seasonCount=3,
        description="A lone Mandalorian bounty hunter navigates the outer reaches of the galaxy while protecting a mysterious Child.",
        tags=listOf("Sci-Fi","Action","Western"),
        seasons= mapOf(
            1 to eps("mando",1, listOf(
                "Chapter 1: The Mandalorian" to "A seasoned Mandalorian accepts a very mysterious and secretive new job.",
                "Chapter 2: The Child" to "The Mandalorian discovers a remarkable asset that changes all his plans.",
                "Chapter 3: The Sin" to "The Mandalorian makes a difficult moral decision that puts him at odds with his guild.",
                "Chapter 4: Sanctuary" to "The Mandalorian and the Child hide out on a peaceful farming planet.",
                "Chapter 5: The Gunslinger" to "The Mandalorian accepts help from an inexperienced young bounty hunter.",
                "Chapter 6: The Prisoner" to "Mercenaries convince the Mandalorian to help break into a max security prison.",
                "Chapter 7: The Reckoning" to "The Mandalorian and allies prepare to confront the dangerous Client.",
                "Chapter 8: Redemption" to "The Mandalorian and his allies fight desperately against an Imperial attack.",
            )),
        )
    ),
    // ── DRAMA ────────────────────────────────────────────────────────
    Show(
        id="game-of-thrones", title="Game of Thrones", category="drama", badge="⚔️",
        color=Color(0xFF7B1D1D), matchPercent=99, rating="TV-MA", year=2011, seasonCount=8,
        description="Noble families clash in a brutal struggle for control of Westeros while an ancient evil awakens in the frozen north.",
        tags=listOf("Fantasy","Drama","Action"),
        seasons= mapOf(
            1 to eps("got",1, listOf(
                "Winter Is Coming" to "The noble Stark family is drawn into a deadly web of royal intrigue.",
                "The Kingsroad" to "Eddard Stark takes his daughters south to King's Landing.",
                "Lord Snow" to "Jon Snow arrives at Castle Black and discovers the Night's Watch is different.",
                "Cripples, Bastards, and Broken Things" to "Jon struggles to find his place among the Night's Watch.",
                "The Wolf and the Lion" to "A grand tournament is held in King Robert's honor.",
                "A Golden Crown" to "Daenerys receives an extraordinarily cruel gift from Khal Drogo.",
                "You Win or You Die" to "Ned Stark uncovers the terrible truth about King Joffrey's parentage.",
                "The Pointy End" to "The Lannisters make their bold deadly move against the Stark family.",
                "Baelor" to "A shocking decision forever seals the fate of a beloved character.",
                "Fire and Blood" to "The Stark family must come to terms with devastating news.",
            )),
            2 to eps("got",2, listOf(
                "The North Remembers" to "Joffrey celebrates his rule as dangerous enemies begin to gather.",
                "The Night Lands" to "Theon Greyjoy returns to the Iron Islands after a long absence.",
                "What Is Dead May Never Die" to "Tyrion cleverly tests the loyalty of each member of the small council.",
                "Garden of Bones" to "Joffrey takes out his anger on Sansa after an embarrassing defeat.",
                "The Ghost of Harrenhal" to "The fate of Renly Baratheon is brutally sealed in the darkness of night.",
            )),
        )
    ),
    Show(
        id="succession", title="Succession", category="drama", badge="💼",
        color=Color(0xFF1A2C4E), matchPercent=98, rating="TV-MA", year=2018, seasonCount=4,
        description="A billionaire media family tears itself apart competing for control of the empire as the patriarch's health fails.",
        tags=listOf("Drama","Satire","Family"),
        seasons= mapOf(
            1 to eps("succ",1, listOf(
                "Celebration" to "Logan Roy's 80th birthday reveals dangerously deep tensions in the Roy family.",
                "Shit Show at the Fuck Factory" to "A crisis forces the Roy children to scramble desperately for control.",
                "Lifeboats" to "The Roy children work frantically to stabilize the company as alliances shift.",
                "Sad Sack Wasp Trap" to "Logan confronts a major PR crisis while Kendall struggles for influence.",
                "I Went to Market" to "The family's ruthlessly ambitious dealings finally begin to come to a head.",
            )),
        )
    ),
    Show(
        id="the-crown", title="The Crown", category="drama", badge="👑",
        color=Color(0xFF1A3A5C), matchPercent=96, rating="TV-MA", year=2016, seasonCount=6,
        description="The reign of Queen Elizabeth II is explored through key political events that shaped the British monarchy.",
        tags=listOf("Drama","Historical","Biography"),
        seasons= mapOf(
            1 to eps("crown",1, listOf(
                "Wolferton Splash" to "Prince Philip marries Princess Elizabeth who inherits an enormous responsibility.",
                "Hyde Park Corner" to "King George VI undergoes surgery, forcing Elizabeth to step up her duties.",
                "Windsor" to "The new Queen must decide between personal duty and family love.",
                "Act of God" to "A deadly smog blankets London and the government is paralyzed.",
                "Smoke and Mirrors" to "Philip chafes against the rigid protocol of a coronation.",
            )),
        )
    ),
    Show(
        id="ozark", title="Ozark", category="drama", badge="💰",
        color=Color(0xFF0A2A3A), matchPercent=97, rating="TV-MA", year=2017, seasonCount=4,
        description="A financial advisor drags his family to the Missouri Ozarks after a money-laundering scheme goes catastrophically wrong.",
        tags=listOf("Crime","Thriller","Drama"),
        seasons= mapOf(
            1 to eps("ozark",1, listOf(
                "Sugarwood" to "Marty Byrde must relocate his family to the Ozarks after a money-laundering scheme goes wrong.",
                "Blue Cat" to "Marty struggles to legitimize his criminal operation through a marina business.",
                "My Dripping Sleep" to "Marty faces threats from both the cartel and local criminals.",
                "Tonight We Improvise" to "A dangerous local criminal makes life increasingly difficult for the Byrde family.",
                "Ruling Days" to "Wendy slowly understands the full extent of their dangerous situation.",
            )),
        )
    ),
    // ── COMEDY ───────────────────────────────────────────────────────
    Show(
        id="the-office", title="The Office", category="comedy", badge="📋",
        color=Color(0xFF3D6B97), matchPercent=99, rating="TV-14", year=2005, seasonCount=9,
        description="A mockumentary-style look at the hilariously awkward lives of workers at Scranton's Dunder Mifflin branch.",
        tags=listOf("Comedy","Mockumentary","Workplace"),
        seasons= mapOf(
            1 to eps("office",1, listOf(
                "Pilot" to "Michael Scott introduces his unique management style to a documentary film crew.",
                "Diversity Day" to "Michael conducts a wildly inappropriate diversity training session.",
                "Health Care" to "Michael puts Dwight in charge of selecting a health care plan with terrible results.",
                "The Alliance" to "Dwight forms a bizarre secret alliance with his nemesis Jim.",
                "Basketball" to "Michael challenges the warehouse staff to a competitive game of basketball.",
                "Hot Girl" to "A saleswoman visits the office and sparks intense competition among the men.",
            )),
            2 to eps("office",2, listOf(
                "The Dundies" to "Michael hosts his beloved annual Dundie Awards ceremony at Chili's.",
                "Sexual Harassment" to "A corporate visit leads to an embarrassing sexual harassment seminar.",
                "Office Olympics" to "Jim organizes hilarious office games while Michael is away.",
                "The Fire" to "A mysterious fire alarm forces the entire office to wait outside.",
                "Halloween" to "Michael must fire one person from the office on Halloween.",
            )),
        )
    ),
    Show(
        id="brooklyn-99", title="Brooklyn Nine-Nine", category="comedy", badge="🚔",
        color=Color(0xFF1A5276), matchPercent=98, rating="TV-14", year=2013, seasonCount=8,
        description="Hilarious ensemble comedy following the quirky detectives of the fictional 99th Precinct of the NYPD.",
        tags=listOf("Comedy","Crime","Workplace"),
        seasons= mapOf(
            1 to eps("b99",1, listOf(
                "Pilot" to "Jake Peralta and the quirky detectives of the 99th Precinct are introduced.",
                "The Tagger" to "Jake and Captain Holt investigate a serial vandalism case.",
                "The Slump" to "The detectives struggle with a slump in their case-closing rate.",
                "M.E. Time" to "Jake's medical examiner friend helps the team with a tricky case.",
                "The Vulture" to "A notorious case-stealing detective causes everyone serious headaches.",
            )),
        )
    ),
    Show(
        id="ted-lasso", title="Ted Lasso", category="comedy", badge="⚽",
        color=Color(0xFF1A6B3A), matchPercent=98, rating="TV-MA", year=2020, seasonCount=3,
        description="An American college football coach is hired to manage an English soccer team despite having zero experience.",
        tags=listOf("Comedy","Sports","Feel-Good"),
        seasons= mapOf(
            1 to eps("ted",1, listOf(
                "Pilot" to "Optimistic American coach Ted Lasso arrives in England unprepared for the Premier League.",
                "Biscuits" to "Ted tries to win over the skeptical owner of AFC Richmond with his special biscuits.",
                "Trent Crimm: The Independent" to "A journalist shadows Ted to write a profile that could make or break his career.",
                "For the Children" to "The team attends a charity auction and Ted makes a heartfelt gesture.",
                "Tan Lines" to "Ted makes a controversial lineup decision that divides the locker room.",
                "Two Aces" to "The team gets a new star player while Ted resolves tensions between teammates.",
            )),
        )
    ),
    Show(
        id="parks-rec", title="Parks and Recreation", category="comedy", badge="🌳",
        color=Color(0xFF1A6B3A), matchPercent=97, rating="TV-PG", year=2009, seasonCount=7,
        description="The absurd bureaucratic antics of an idealistic parks official in the wonderfully weird city of Pawnee, Indiana.",
        tags=listOf("Comedy","Political","Mockumentary"),
        seasons= mapOf(
            1 to eps("parks",1, listOf(
                "Pilot" to "Enthusiastic Leslie Knope embarks on her mission to fill a pit with a beautiful park.",
                "Canvassing" to "Leslie polls local residents about her plan to convert the pit into a park.",
                "The Reporter" to "A local newspaper reporter comes to do a story on Leslie.",
                "Boys' Club" to "Leslie realizes she is not included in the male-dominated boys' club at work.",
                "The Banquet" to "Leslie receives a prestigious award and brings her difficult mother.",
                "Rock Show" to "Ann's boyfriend invites the entire parks department to his rock show.",
            )),
        )
    ),
)

val CATEGORIES = listOf(
    "trending" to "🔥 Trending Now",
    "kids"     to "🧒 Kids",
    "action"   to "⚡ Action & Thriller",
    "drama"    to "🎭 Drama",
    "comedy"   to "😂 Comedy",
)

fun getShowsForCategory(category: String): List<Show> = when (category) {
    "trending" -> ALL_SHOWS.sortedByDescending { it.matchPercent }.take(12)
    else       -> ALL_SHOWS.filter { it.category == category }
}

val HERO_SHOW = ALL_SHOWS.first { it.id == "stranger-things" }
