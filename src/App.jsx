import { useState, useRef } from "react";
import { GoogleOAuthProvider, useGoogleLogin } from "@react-oauth/google";
import { Search, Bell, Play, Info, ChevronLeft, ChevronRight, Check, LogOut } from "lucide-react";

// ════════════════════════════════════════════════════
// DESIGN TOKENS
// ════════════════════════════════════════════════════
const C = {
  bg:"#05050e", surf:"#0c0c1e", card:"#111126", cardHov:"#17173a",
  border:"rgba(255,255,255,0.06)", borderBright:"rgba(255,255,255,0.13)",
  accent:"#e50914", accentGlow:"rgba(229,9,20,0.25)",
  text:"#ffffff", sub:"#8888aa", muted:"#3e3e62", green:"#46d369",
};
const F = "'Outfit','Segoe UI',system-ui,sans-serif";
const D = "'Bebas Neue',Impact,sans-serif";

// ════════════════════════════════════════════════════
// DATA HELPERS
// ════════════════════════════════════════════════════
const mk = (id, sn, list) => list.map((x, i) => ({
  n: i+1, title: x.t, desc: x.d,
  dur: `${18+((id.charCodeAt(0)*7+sn*3+i*11)%28)} min`,
  thumb: `https://picsum.photos/seed/${id}-${sn}-${i+1}/400/400`,
}));
const genEps = (id, sn, ct=8) => Array.from({length:ct},(_,i)=>({
  n:i+1, title:`Episode ${i+1}`,
  desc:`Season ${sn}, Episode ${i+1} — the story continues with new twists and surprising turns.`,
  dur:`${20+((id.charCodeAt(0)+i*3)%18)} min`,
  thumb:`https://picsum.photos/seed/${id}-${sn}-${i+1}/400/400`,
}));
const getEps = (show, sn) => show.s[sn] || genEps(show.id, sn);

// ════════════════════════════════════════════════════
// SHOWS DATABASE
// ════════════════════════════════════════════════════
const SHOWS = [
  // ── KIDS ─────────────────────────────────────────
  { id:"paw-patrol", title:"PAW Patrol", cat:"kids", badge:"🐾", hue:"#1a73e8", match:98, rating:"TV-Y", yr:2013, sc:10,
    desc:"Ryder and his rescue dogs protect Adventure Bay from every kind of emergency — big and small.",
    tags:["Animation","Adventure","Family"],
    s:{
      1:mk("pp",1,[
        {t:"Pups Make Their Mark",d:"The pups must save Mayor Goodway's crumbling lighthouse before it collapses into the bay."},
        {t:"Pups Fall Festival",d:"A runaway tractor threatens to completely ruin the town's beloved annual fall festival."},
        {t:"Pups Save the Sea Turtles",d:"Lost baby sea turtles desperately need help finding their way back to the ocean."},
        {t:"Pup Pup Goose",d:"A confused goose family struggles to begin their winter migration heading south."},
        {t:"Pups Save the Bunnies",d:"Three fluffy bunnies become hopelessly trapped inside a collapsed old mine shaft."},
        {t:"Pup Pup and Away",d:"Mayor Goodway drifts high into the sky when her hot air balloon floats uncontrolled."},
        {t:"Pups on Ice",d:"Chickaletta the chicken falls through thin ice on the frozen bay and needs immediate rescue."},
        {t:"Pups and the Snow Monster",d:"The terrifying legend of the Snow Monster sends all of Foggy Bottom into a full panic."},
      ]),
      2:mk("pp",2,[
        {t:"Pups Save a Train",d:"A runaway train must be stopped immediately before it causes a massive catastrophe."},
        {t:"Pups in a Fog",d:"Mayor Goodway becomes stranded on Seal Island in the thick and dangerous fog."},
        {t:"Pups Save Jake's Cake",d:"The team races to deliver Jake's birthday cake safely over the snowy mountain."},
        {t:"Pups Save the Camping Trip",d:"Ryder and the pups get lost during what was supposed to be a fun camping trip."},
        {t:"Pups and the Kitty-tastrophe",d:"An adventurous kitten gets tangled up in quite a wild situation needing rescue."},
        {t:"Pups Save a Mer-Pup",d:"The pups discover something truly magical hidden beneath the deep bay waters."},
      ]),
      3:mk("pp",3,[
        {t:"Pups in a Fix",d:"Multiple emergencies break out across Adventure Bay at the very same time."},
        {t:"Pups Save a Flying Frog",d:"A frog with rocket-powered legs causes complete chaos throughout the entire city."},
        {t:"Pups in a Jam",d:"The berry jam festival goes awry when all the different jellies get hopelessly mixed up."},
        {t:"Pups Save the Parrot",d:"A clever parrot leads the whole PAW Patrol team on a wild treasure hunt."},
        {t:"Pups Go All Monkey",d:"Escaped monkeys from the animal sanctuary cause absolute mischief everywhere."},
      ]),
      4:mk("pp",4,[
        {t:"Pups Save a Blimp",d:"A runaway blimp carrying Mayor Goodway must be rescued from high in the clouds."},
        {t:"Pups Save an Ace",d:"Daredevil pilot Ace Sorensen needs urgent help landing her badly damaged plane."},
        {t:"Pups Save a Basketball Game",d:"The big championship game needs the PAW Patrol's urgent intervention."},
        {t:"Pups Save a Mayor and a Ladybug",d:"A tiny ladybug causes surprisingly big problems for the mayor of Adventure Bay."},
      ]),
    }
  },
  { id:"dora", title:"Dora the Explorer", cat:"kids", badge:"🗺️", hue:"#c44b1d", match:96, rating:"TV-Y", yr:2000, sc:8,
    desc:"Join Dora and her monkey friend Boots on interactive adventures that teach Spanish and critical thinking.",
    tags:["Animation","Educational","Adventure"],
    s:{
      1:mk("dora",1,[
        {t:"The Legend of the Big Red Chicken",d:"Dora and Boots set off to find the magical Big Red Chicken who is said to grant wishes."},
        {t:"Lost and Found",d:"A baby bluebird has gotten hopelessly lost and needs urgent help getting home."},
        {t:"Hic-Boom-Ohhh!",d:"Dora and Boots help a poor elephant who simply cannot stop hiccuping."},
        {t:"Beaches",d:"Dora and Boots take an exciting adventure all the way to the beach."},
        {t:"We All Scream for Ice Cream",d:"The ice cream truck has lost all its treats and Dora must help recover them."},
        {t:"Choo-Choo!",d:"The train is stuck at the station and Dora cleverly figures out exactly how to fix it."},
      ]),
      2:mk("dora",2,[
        {t:"The Big Storm",d:"A powerful thunderstorm cuts all the lights and Dora helps frightened animals find shelter."},
        {t:"The Magic Stick",d:"A magical stick Dora discovers leads to a completely unexpected and wonderful adventure."},
        {t:"The Missing Piece",d:"Dora searches high and low to find one crucial missing puzzle piece."},
        {t:"Rojo the Fire Truck",d:"Rojo the brave fire truck is in serious trouble and Dora is ready to help."},
        {t:"Lost Squeaky",d:"Boots has lost his most beloved toy and is completely heartbroken about it."},
      ]),
    }
  },
  { id:"bluey", title:"Bluey", cat:"kids", badge:"🐕", hue:"#0094c6", match:99, rating:"TV-Y", yr:2018, sc:3,
    desc:"An imaginative Blue Heeler puppy finds wonder and adventure in everyday life with her family in Brisbane.",
    tags:["Animation","Family","Comedy"],
    s:{
      1:mk("bluey",1,[
        {t:"Bike Ride",d:"Bluey learns to ride her bike with Dad's seemingly endless patience and encouragement."},
        {t:"Taxi",d:"Dad transforms into a taxi driver for Bluey and Bingo's wonderfully creative game."},
        {t:"Zoo",d:"The whole Heeler family creates their very own magnificent zoo together at home."},
        {t:"Hospital",d:"Bluey and Bingo play hospital and nurse all their beloved stuffed animals back to health."},
        {t:"Takeaway",d:"Dad tries to collect the family dinner while wrangling two very energetic young pups."},
        {t:"Dad Baby",d:"Dad plays along with a bizarre but absolutely hilarious imaginative game."},
        {t:"Markets",d:"The Heeler family spends a beautiful morning exploring the local weekend market."},
        {t:"Neighbours",d:"Bluey makes a wonderful and unexpected new friend from right across the neighborhood."},
      ]),
      2:mk("bluey",2,[
        {t:"Hammerbarn",d:"The whole Heeler family packs up for a wonderful vacation at a lovely farm stay."},
        {t:"The Pool",d:"What starts as a normal pool day quickly turns into quite the adventure."},
        {t:"Baby Race",d:"Mum fondly remembers when both Bluey and Bingo were just learning to walk."},
        {t:"Bus Ride",d:"Bluey and Bingo excitedly ride the big city bus together for their very first time."},
        {t:"Sleepytime",d:"Little Bingo has a magical and beautiful cosmic adventure while falling asleep."},
      ]),
      3:mk("bluey",3,[
        {t:"Perfect",d:"Family trivia night becomes intensely competitive as all the Heelers go all in."},
        {t:"Stories",d:"Bluey and Bingo each tell their own very different version of a story about their day."},
        {t:"Tradies",d:"Tradespeople come to fix the house and Bluey is utterly fascinated by them."},
        {t:"Smoochy Kiss",d:"Jack McKenzie comes over for a playdate with Bluey that gets very competitive."},
      ]),
    }
  },
  { id:"peppa-pig", title:"Peppa Pig", cat:"kids", badge:"🐷", hue:"#d4578a", match:97, rating:"TV-Y", yr:2004, sc:8,
    desc:"Peppa is a loveable, cheeky little piggy who loves muddy puddles and spending time with her beloved family.",
    tags:["Animation","Family","Preschool"],
    s:{
      1:mk("peppa",1,[
        {t:"Muddy Puddles",d:"Peppa and George head outside to jump enthusiastically in lots and lots of muddy puddles."},
        {t:"The Playground",d:"Peppa meets up with all her friends for a wonderful fun day at the playground."},
        {t:"Best Friend",d:"Peppa makes an exciting new best friend at her much-loved nursery school."},
        {t:"Polly Parrot",d:"Peppa and her whole family pay a visit to Granny's colorful and chatty parrot."},
        {t:"At the Seaside",d:"The whole Pig family drives to the beautiful seaside for an amazing day out."},
        {t:"Swimming",d:"Peppa and her brother George visit the local pool to begin learning how to swim."},
      ]),
      2:mk("peppa",2,[
        {t:"Camping",d:"The Pig family packs up and heads out on an exciting countryside camping adventure."},
        {t:"Ballet Lesson",d:"Peppa attends her very first ballet lesson and completely falls in love with it."},
        {t:"Daddy's Movie Camera",d:"Daddy Pig films the family as they make their own hilarious home movie together."},
        {t:"New Shoes",d:"Everyone in the Pig family urgently needs new shoes and heads off to the shops."},
      ]),
    }
  },
  { id:"spongebob", title:"SpongeBob SquarePants", cat:"kids", badge:"🧽", hue:"#e8b800", match:97, rating:"TV-Y7", yr:1999, sc:14,
    desc:"The absurd, silly adventures of a naive sea sponge and his dim-witted starfish best friend in Bikini Bottom.",
    tags:["Animation","Comedy","Adventure"],
    s:{
      1:mk("sponge",1,[
        {t:"Help Wanted / Tea at the Treedome",d:"SpongeBob applies for a job at the Krusty Krab and meets Sandy the squirrel."},
        {t:"Bubblestand / Ripped Pants",d:"SpongeBob sets up a bubble-blowing stand and embarrasses himself at the beach."},
        {t:"Jellyfishing / Plankton!",d:"Patrick and SpongeBob go jellyfishing while the villainous Plankton makes his debut."},
        {t:"Naughty Nautical Neighbors / Boating School",d:"SpongeBob repeatedly fails his boating exam at Mrs. Puff's driving school."},
        {t:"Pizza Delivery / Home Sweet Pineapple",d:"Squidward and SpongeBob must deliver a single pizza all the way across the ocean."},
        {t:"Sandy's Rocket / Squeaky Boots",d:"SpongeBob and Patrick accidentally blast off to the moon in Sandy's rocket."},
      ]),
      2:mk("sponge",2,[
        {t:"Your Shoe's Untied / Squid's Day Off",d:"SpongeBob completely forgets how to tie his own shoes and must relearn."},
        {t:"Something Smells / Bossy Boots",d:"SpongeBob creates a hideous-smelling sundae that completely repels everyone."},
        {t:"Big Pink Loser / Bubble Buddy",d:"Patrick desperately tries to win an award at literally any single thing."},
        {t:"Dying for Pie / Imitation Krabs",d:"Squidward accidentally gives SpongeBob a pie that turns out to be a bomb."},
      ]),
    }
  },
  { id:"sesame-street", title:"Sesame Street", cat:"kids", badge:"🎭", hue:"#e8621a", match:98, rating:"TV-Y", yr:1969, sc:53,
    desc:"Big Bird, Elmo, Cookie Monster and friends help children joyfully learn letters, numbers, and life skills.",
    tags:["Educational","Animation","Family"],
    s:{
      1:mk("sesame",1,[
        {t:"A Brand New Street",d:"Welcome to Sesame Street! Meet all the wonderful residents for the very first time!"},
        {t:"The Letter B",d:"Big Bird and friends explore all the amazing things that start with the letter B."},
        {t:"A Visit to the Library",d:"Gordon takes all the neighborhood children on a special trip to the local library."},
        {t:"Mr. Hooper's Store",d:"Mr. Hooper teaches the kids all about what it's like to run a neighborhood store."},
        {t:"The Number Five",d:"The whole Sesame Street gang celebrates the number five in many fun and creative ways."},
      ]),
      2:mk("sesame",2,[
        {t:"New Friends",d:"Wonderful new friends come to join the beloved Sesame Street neighborhood."},
        {t:"Cookie Monster's Cookies",d:"Cookie Monster tries his hardest to learn about sharing — sort of successfully!"},
        {t:"Feelings",d:"The whole gang explores different emotions and what it truly means to have feelings."},
        {t:"The Counting Game",d:"Count von Count helps everyone learn to count all the way to ten in a fun new way."},
      ]),
    }
  },
  { id:"cocomelon", title:"CoComelon", cat:"kids", badge:"🎵", hue:"#3ab5e0", match:95, rating:"TV-Y", yr:2006, sc:6,
    desc:"JJ and friends learn about the world through catchy songs, nursery rhymes, and colorful adventures.",
    tags:["Animation","Musical","Preschool"],
    s:{
      1:mk("coco",1,[
        {t:"Bath Song",d:"JJ discovers that bath time can actually be really fun with the right attitude."},
        {t:"Yes Yes Vegetables",d:"JJ makes the surprising discovery that vegetables are actually pretty tasty after all!"},
        {t:"Boo Boo Song",d:"JJ gets a little boo boo and learns exactly what to do to start feeling better."},
        {t:"Wheels on the Bus",d:"JJ and all his friends take a super fun trip on their bright yellow school bus."},
        {t:"Old MacDonald",d:"JJ visits Old MacDonald's farm and gets to meet all the exciting animals."},
      ]),
      2:mk("coco",2,[
        {t:"Doctor Checkup Song",d:"JJ goes to the doctor and bravely learns that checkups are nothing to be scared of."},
        {t:"The Sharing Song",d:"JJ learns the important lesson about sharing nicely with all of his friends."},
        {t:"Clean Up Song",d:"JJ discovers that cleaning up can be very fun with exactly the right song."},
      ]),
    }
  },
  { id:"mickey-mouse", title:"Mickey Mouse Clubhouse", cat:"kids", badge:"🐭", hue:"#d01b24", match:96, rating:"TV-Y", yr:2006, sc:4,
    desc:"Mickey Mouse and his pals use magical Mouseketools to solve problems and go on adventures at the Clubhouse.",
    tags:["Animation","Educational","Family"],
    s:{
      1:mk("mickey",1,[
        {t:"Mickey's Great Outdoors",d:"Mickey and all his friends head outside for a very exciting outdoor camping adventure."},
        {t:"Minnie's Birthday",d:"The whole gang secretly plans a wonderful surprise birthday party for dear Minnie."},
        {t:"Donald and the Beanstalk",d:"Donald and Mickey discover a magical beanstalk growing right in the garden."},
        {t:"Goofy's Bird",d:"Goofy finds a very mysterious bird and tries to figure out exactly what kind it is."},
        {t:"Pluto's Ball",d:"Pluto's favorite ball has rolled completely away and the whole gang helps find it."},
      ]),
      2:mk("mickey",2,[
        {t:"Mickey's Treasure Hunt",d:"Mickey finds an old treasure map and leads his friends on an exciting hunt."},
        {t:"Donald's Ducks",d:"Donald gets a surprise visit from his three mischievous little duck nephews."},
        {t:"Minnie's Rainbow",d:"After a rain shower, Minnie sets out to find the magical end of a beautiful rainbow."},
      ]),
    }
  },
  { id:"scooby-doo", title:"Scooby-Doo", cat:"kids", badge:"🐶", hue:"#6a4c9c", match:94, rating:"TV-Y7", yr:1969, sc:13,
    desc:"Scooby-Doo and Mystery Inc. solve spooky mysteries involving supposedly supernatural creatures across America.",
    tags:["Animation","Mystery","Comedy"],
    s:{
      1:mk("scooby",1,[
        {t:"What a Night for a Knight",d:"The gang investigates a mysterious Black Knight haunting a museum."},
        {t:"A Clue for Scooby Doo",d:"A ghostly diver attacks boaters off the coast and the gang investigates."},
        {t:"Hassle in the Castle",d:"Mystery Inc. investigates a haunted castle on a foggy and mysterious island."},
        {t:"Mine Your Own Business",d:"A ghost miner terrorizes an old gold mine and the gang must unmask the culprit."},
        {t:"Decoy for a Dognapper",d:"A dog show is thrown into chaos by a cunning and mysterious dognapper."},
        {t:"What the Hex Going On?",d:"A ghostly hex man is supposedly turning people into zombies around the town."},
      ]),
    }
  },
  { id:"loud-house", title:"The Loud House", cat:"kids", badge:"🏠", hue:"#e8961a", match:94, rating:"TV-Y7", yr:2016, sc:6,
    desc:"Lincoln Loud navigates life in a hectic household as the only boy with ten very different sisters.",
    tags:["Animation","Comedy","Family"],
    s:{
      1:mk("loud",1,[
        {t:"Left in the Dark / Get the Message",d:"Lincoln schemes to watch his favorite show in peace while his sisters compete for the TV."},
        {t:"Heavy Meddle / Making the Case",d:"Lincoln accidentally kisses a girl at school and videos go viral for all the wrong reasons."},
        {t:"Driving Miss Hazy / No Guts, No Glori",d:"Lori needs driving practice and Lincoln makes a deal with the bossy substitute sister."},
        {t:"The Sweet Spot / A Tale of Two Tables",d:"Lincoln tries to claim the best seat in the van for the family road trip."},
        {t:"Project Loud House / In Tents Debate",d:"A typical morning in the Loud house turns into a hilarious series of disasters."},
      ]),
    }
  },
  // ── ACTION & THRILLER ────────────────────────────
  { id:"breaking-bad", title:"Breaking Bad", cat:"action", badge:"⚗️", hue:"#2d7d46", match:99, rating:"TV-MA", yr:2008, sc:5,
    desc:"A chemistry teacher turned meth manufacturer partners with a former student to build a criminal empire, piece by piece.",
    tags:["Crime","Drama","Thriller"],
    s:{
      1:mk("bb",1,[
        {t:"Pilot",d:"Chemistry teacher Walter White receives shocking news and makes a life-altering decision."},
        {t:"Cat's in the Bag",d:"Walt and Jesse must deal with the dangerous aftermath of their very first cook."},
        {t:"...And the Bag's in the River",d:"Walter is forced to make an excruciating and terrible moral choice entirely on his own."},
        {t:"Cancer Man",d:"Walter's family discovers the difficult truth about his cancer diagnosis."},
        {t:"Gray Matter",d:"An unexpected offer from old friends forces Walt to confront what he truly wants."},
        {t:"Crazy Handful of Nothin'",d:"Walt sets up a tense and extremely dangerous meeting with Tuco, a violent drug distributor."},
        {t:"A No-Rough-Stuff-Type Deal",d:"Walt and Jesse attempt to acquire a crucial chemical ingredient for their operation."},
      ]),
      2:mk("bb",2,[
        {t:"Seven Thirty-Seven",d:"A deadly new threat from Tuco forces Walt and Jesse to make fast and drastic plans."},
        {t:"Down",d:"Jesse hits devastating rock bottom while Walt struggles with his double life."},
        {t:"Bit by a Dead Bee",d:"Walt and Jesse must construct elaborate cover stories for their mysterious absence."},
        {t:"Over",d:"Walt makes a surprising announcement and his alarming behavior begins to change."},
        {t:"Better Call Saul",d:"Walt and Jesse are introduced to a colorful and resourceful criminal defense lawyer."},
      ]),
      3:mk("bb",3,[
        {t:"No Más",d:"Walt resolves to leave the dangerous drug trade behind and return to normal life."},
        {t:"Caballo Sin Nombre",d:"Two mysterious and deadly assassins arrive in Albuquerque with a very specific agenda."},
        {t:"I.F.T.",d:"Skyler delivers a shocking and utterly devastating ultimatum to Walter at their home."},
        {t:"Green Light",d:"Jesse attempts to cook independently with completely unexpected and troubling results."},
        {t:"Más",d:"Walt is offered a stunning state-of-the-art laboratory and an extremely lucrative deal."},
      ]),
    }
  },
  { id:"stranger-things", title:"Stranger Things", cat:"action", badge:"🌀", hue:"#3d1a6e", match:98, rating:"TV-14", yr:2016, sc:4,
    desc:"A boy's disappearance in a small Indiana town reveals a government conspiracy and a terrifying alternate dimension.",
    tags:["Horror","Sci-Fi","Mystery"],
    s:{
      1:mk("st",1,[
        {t:"Chapter One: The Vanishing of Will Byers",d:"A young boy vanishes and strange supernatural forces start to emerge in Hawkins, Indiana."},
        {t:"Chapter Two: The Weirdo on Maple Street",d:"A mysterious girl with psychic powers is found and taken in by Will's best friends."},
        {t:"Chapter Three: Holly, Jolly",d:"Joyce receives an eerie and deeply terrifying otherworldly message from her missing son."},
        {t:"Chapter Four: The Body",d:"Authorities search for answers while the kids continue their own dangerous investigation."},
        {t:"Chapter Five: The Flea and the Acrobat",d:"The boys work desperately to understand the terrifying dimension called the Upside Down."},
        {t:"Chapter Six: The Monster",d:"Eleven reveals the shocking and terrible truth about the creature stalking Hawkins."},
        {t:"Chapter Seven: The Bathtub",d:"Eleven makes a desperate and dangerous attempt to locate both Will and Barbara."},
        {t:"Chapter Eight: The Upside Down",d:"Hopper and Joyce plunge into the terrifying Upside Down to finally rescue Will."},
      ]),
      2:mk("st",2,[
        {t:"Chapter One: MADMAX",d:"A mysterious new girl arrives in Hawkins with a troubled and dangerous hidden past."},
        {t:"Chapter Two: Trick or Treat, Freak",d:"Will continues experiencing deeply frightening visions from the dark Upside Down."},
        {t:"Chapter Three: The Pollywog",d:"A strange creature Will discovered begins to rapidly grow and terrifyingly evolve."},
        {t:"Chapter Four: Will the Wise",d:"Will has a harrowing encounter with the towering and terrible Shadow Monster."},
      ]),
    }
  },
  { id:"the-boys", title:"The Boys", cat:"action", badge:"⚡", hue:"#b01a1a", match:99, rating:"TV-MA", yr:2019, sc:4,
    desc:"A group of vigilantes set out to expose and take down corrupt superheroes who abuse their powers for a mega-corporation.",
    tags:["Action","Satire","Superhero"],
    s:{
      1:mk("boys",1,[
        {t:"The Name of the Game",d:"Hughie Campbell's world is shattered and he joins a shadowy team out to expose The Seven."},
        {t:"Cherry",d:"Billy Butcher recruits Hughie for a dangerous plan to target the world's most powerful superhero."},
        {t:"Get Some",d:"The team discovers Translucent is spying on them and must find a way to stop him."},
        {t:"The Female of the Species",d:"The boys try to smuggle a mysterious and incredibly dangerous woman out of the country."},
        {t:"Good for the Soul",d:"Homelander and Starlight attend a massive Christian music festival for major publicity."},
        {t:"The Innocents",d:"Butcher searches desperately for his missing wife while Homelander's dark side is revealed."},
        {t:"The Self-Preservation Society",d:"The boys find themselves in serious danger from both Vought and the authorities."},
        {t:"You Found Me",d:"The boys face their most dangerous confrontation yet as the truth about Compound V is exposed."},
      ]),
      2:mk("boys",2,[
        {t:"The Big Ride",d:"The boys are on the run while Vought International works to cover up their crimes."},
        {t:"Proper Preparation and Planning",d:"The boys investigate a series of mysterious supe terrorist attacks around the world."},
        {t:"Over the Hill with the Swords of a Thousand Men",d:"A shocking incident at sea leads to a massive crisis for the team."},
        {t:"Nothing Like It in the World",d:"Butcher and Mother's Milk go on a road trip to investigate a lead on Stormfront."},
      ]),
    }
  },
  { id:"mandalorian", title:"The Mandalorian", cat:"action", badge:"🪖", hue:"#5a4030", match:98, rating:"TV-14", yr:2019, sc:3,
    desc:"A lone Mandalorian bounty hunter navigates the outer reaches of the galaxy while protecting a mysterious Child.",
    tags:["Sci-Fi","Action","Western"],
    s:{
      1:mk("mando",1,[
        {t:"Chapter 1: The Mandalorian",d:"A seasoned Mandalorian bounty hunter accepts a very mysterious and secretive new job."},
        {t:"Chapter 2: The Child",d:"The Mandalorian discovers a remarkable asset that changes all of his plans entirely."},
        {t:"Chapter 3: The Sin",d:"The Mandalorian makes a difficult moral decision that puts him at odds with his guild."},
        {t:"Chapter 4: Sanctuary",d:"The Mandalorian and the Child hide out on a peaceful farming planet seeking safety."},
        {t:"Chapter 5: The Gunslinger",d:"The Mandalorian accepts help from an inexperienced young bounty hunter on Tatooine."},
        {t:"Chapter 6: The Prisoner",d:"A crew of mercenaries convinces the Mandalorian to help break into a max security prison."},
        {t:"Chapter 7: The Reckoning",d:"The Mandalorian and allies prepare to confront the dangerous Client and his Imperial forces."},
        {t:"Chapter 8: Redemption",d:"The Mandalorian and his allies fight desperately against an overwhelming Imperial attack."},
      ]),
      2:mk("mando",2,[
        {t:"Chapter 9: The Marshal",d:"The Mandalorian sets out to find other Mandalorians and encounters a very surprising face."},
        {t:"Chapter 10: The Passenger",d:"The Mandalorian must transport a passenger and her precious eggs to a distant planet."},
        {t:"Chapter 11: The Heiress",d:"The Mandalorian finally meets other true Mandalorians with very surprising and revelatory results."},
        {t:"Chapter 12: The Siege",d:"The Mandalorian and old allies mount a daring raid on an Imperial base together."},
      ]),
    }
  },
  { id:"24", title:"24", cat:"action", badge:"⏱️", hue:"#1a4a7a", match:96, rating:"TV-14", yr:2001, sc:9,
    desc:"CTU agent Jack Bauer races against the clock to stop terrorists in real-time thrillers spanning 24 hours per season.",
    tags:["Action","Thriller","Drama"],
    s:{
      1:mk("24a",1,[
        {t:"12:00 AM - 1:00 AM",d:"A CTU agent learns of a threat to Presidential candidate David Palmer and his family."},
        {t:"1:00 AM - 2:00 AM",d:"Jack Bauer discovers a mole within CTU as the threat to Palmer rapidly escalates."},
        {t:"2:00 AM - 3:00 AM",d:"Bauer makes dangerous new contact while Palmer confronts a devastating family scandal."},
        {t:"3:00 AM - 4:00 AM",d:"A hostage situation forces Jack into increasingly desperate and dangerous choices."},
        {t:"4:00 AM - 5:00 AM",d:"As dawn approaches, Jack must trust someone he barely knows to save his daughter."},
      ]),
    }
  },
  // ── DRAMA ─────────────────────────────────────────
  { id:"game-of-thrones", title:"Game of Thrones", cat:"drama", badge:"⚔️", hue:"#7b1d1d", match:99, rating:"TV-MA", yr:2011, sc:8,
    desc:"Noble families clash in a brutal struggle for control of Westeros while an ancient evil awakens in the frozen north.",
    tags:["Fantasy","Drama","Action"],
    s:{
      1:mk("got",1,[
        {t:"Winter Is Coming",d:"The noble Stark family is drawn into a deadly web of royal intrigue at the king's request."},
        {t:"The Kingsroad",d:"Eddard Stark takes his daughters south to King's Landing to serve as the King's Hand."},
        {t:"Lord Snow",d:"Jon Snow arrives at Castle Black and discovers the Night's Watch is different than imagined."},
        {t:"Cripples, Bastards, and Broken Things",d:"Jon struggles desperately to find his place among the hardened men of the Night's Watch."},
        {t:"The Wolf and the Lion",d:"A grand tournament is held in King Robert's honor in the great city of King's Landing."},
        {t:"A Golden Crown",d:"Daenerys receives an extraordinarily cruel and unforgettable gift from Khal Drogo."},
        {t:"You Win or You Die",d:"Ned Stark finally uncovers the terrible truth about King Joffrey's real parentage."},
        {t:"The Pointy End",d:"The Lannisters make their bold and deadly move against the entire Stark family."},
        {t:"Baelor",d:"A shocking and controversial decision forever seals the fate of a beloved character."},
        {t:"Fire and Blood",d:"The Stark family must come to terms with devastating and heartbreaking news."},
      ]),
      2:mk("got",2,[
        {t:"The North Remembers",d:"Joffrey celebrates his rule as dangerous and powerful enemies begin to gather."},
        {t:"The Night Lands",d:"Theon Greyjoy finally returns to his home on the Iron Islands after a long absence."},
        {t:"What Is Dead May Never Die",d:"Tyrion cleverly tests the loyalty of each member of the small council."},
        {t:"Garden of Bones",d:"Joffrey takes out his anger on Sansa Stark after an embarrassing military defeat."},
        {t:"The Ghost of Harrenhal",d:"The shocking fate of Renly Baratheon is brutally sealed in the darkness of night."},
      ]),
      3:mk("got",3,[
        {t:"Valar Dohaeris",d:"Jon Snow is brought before Mance Rayder, the King Beyond the Wall."},
        {t:"Dark Wings, Dark Words",d:"Bran's group encounters siblings Jojen and Meera Reed in the wilderness."},
        {t:"Walk of Punishment",d:"Robb and Catelyn attend the solemn funeral of her beloved father Lord Hoster Tully."},
        {t:"And Now His Watch Is Ended",d:"The Night's Watch men are pushed to the very limits of their fragile loyalty."},
        {t:"Kissed by Fire",d:"The Hound faces Beric Dondarrion in a dramatic and fiery trial by combat."},
      ]),
    }
  },
  { id:"succession", title:"Succession", cat:"drama", badge:"💼", hue:"#1a2c4e", match:98, rating:"TV-MA", yr:2018, sc:4,
    desc:"A billionaire media family tears itself apart competing for control of the empire as the patriarch's health fails.",
    tags:["Drama","Satire","Family"],
    s:{
      1:mk("succ",1,[
        {t:"Celebration",d:"Logan Roy's 80th birthday reveals dangerously deep and unresolved tensions in the Roy family."},
        {t:"Shit Show at the Fuck Factory",d:"A sudden family crisis forces the Roy children to scramble desperately for control."},
        {t:"Lifeboats",d:"The Roy children work frantically to stabilize the company as alliances begin to shift."},
        {t:"Sad Sack Wasp Trap",d:"Logan confronts a major PR crisis while Kendall desperately struggles for influence."},
        {t:"I Went to Market",d:"The family's ruthlessly ambitious business dealings finally begin to come to a head."},
      ]),
      2:mk("succ",2,[
        {t:"The Summer Palace",d:"The family reunites at their summer home while constant power struggles continue."},
        {t:"Vaulter",d:"Kendall leads a devastatingly brutal acquisition that tests everyone's true loyalty."},
        {t:"Hunting",d:"A private retreat with political allies turns into a deeply uncomfortable and revealing ordeal."},
        {t:"Safe Room",d:"A security scare at Waystar headquarters lays bare all the fractured loyalties."},
      ]),
    }
  },
  { id:"house-dragon", title:"House of the Dragon", cat:"drama", badge:"🐲", hue:"#8a1a00", match:97, rating:"TV-MA", yr:2022, sc:2,
    desc:"The Targaryen civil war 200 years before Game of Thrones — a bloody and brutal battle for the Iron Throne.",
    tags:["Fantasy","Drama","Action"],
    s:{
      1:mk("hotd",1,[
        {t:"The Heirs of the Dragon",d:"King Viserys chooses an heir and the seeds of a devastating civil war are planted."},
        {t:"The Rogue Prince",d:"Prince Daemon's provocative actions push Viserys into a dangerous diplomatic position."},
        {t:"Second of His Name",d:"A tournament is held as Rhaenyra grows increasingly restless with her forced role."},
        {t:"King of the Narrow Sea",d:"Daemon returns to King's Landing with an unexpected gift and stirs up dangerous intrigue."},
        {t:"We Light the Way",d:"A tense gathering at court leads to a violent tragedy that changes everything."},
        {t:"The Princess and the Queen",d:"Ten years later, Rhaenyra navigates complex court politics after Viserys's second marriage."},
      ]),
      2:mk("hotd",2,[
        {t:"A Son for a Son",d:"The Dance of the Dragons begins with a devastating act of violence that shocks the realm."},
        {t:"Rhaenyra the Cruel",d:"Rhaenyra's reputation suffers while Alicent consolidates power in the capital."},
        {t:"The Burning Mill",d:"War begins to spread as the great houses must finally choose their side in the conflict."},
        {t:"A Dance of Dragons",d:"Both sides marshal their forces and dragons as the conflict escalates beyond control."},
      ]),
    }
  },
  { id:"the-crown", title:"The Crown", cat:"drama", badge:"👑", hue:"#1a3a5c", match:96, rating:"TV-MA", yr:2016, sc:6,
    desc:"The reign of Queen Elizabeth II is explored through key political and personal events that shaped the British monarchy.",
    tags:["Drama","Historical","Biography"],
    s:{
      1:mk("crown",1,[
        {t:"Wolferton Splash",d:"Prince Philip marries Princess Elizabeth and she inherits an enormous and unexpected responsibility."},
        {t:"Hyde Park Corner",d:"King George VI undergoes serious surgery, forcing Elizabeth to step up her royal duties."},
        {t:"Windsor",d:"The new Queen must make a painful decision between personal duty and the love of family."},
        {t:"Act of God",d:"A deadly smog blankets London and the government is paralyzed by the unfolding crisis."},
        {t:"Smoke and Mirrors",d:"Philip chafes against the rigid protocol of a coronation that diminishes his important role."},
      ]),
    }
  },
  { id:"ozark", title:"Ozark", cat:"drama", badge:"💰", hue:"#0a2a3a", match:97, rating:"TV-MA", yr:2017, sc:4,
    desc:"A financial advisor drags his family from Chicago to the Missouri Ozarks after a money-laundering scheme goes catastrophically wrong.",
    tags:["Crime","Thriller","Drama"],
    s:{
      1:mk("ozark",1,[
        {t:"Sugarwood",d:"Marty Byrde must relocate his family to the Ozarks after a money-laundering scheme goes horribly wrong."},
        {t:"Blue Cat",d:"Marty struggles to legitimize his criminal operation through a local marina business."},
        {t:"My Dripping Sleep",d:"Marty faces threats from both the cartel and local criminals as he sets up his operation."},
        {t:"Tonight We Improvise",d:"A dangerous local criminal figure makes life increasingly difficult for the Byrde family."},
        {t:"Ruling Days",d:"Wendy slowly begins to understand the full and terrifying extent of their dangerous situation."},
      ]),
    }
  },
  // ── COMEDY ────────────────────────────────────────
  { id:"the-office", title:"The Office", cat:"comedy", badge:"📋", hue:"#3d6b97", match:99, rating:"TV-14", yr:2005, sc:9,
    desc:"A mockumentary-style look at the hilariously awkward lives of workers at Scranton's Dunder Mifflin branch.",
    tags:["Comedy","Mockumentary","Workplace"],
    s:{
      1:mk("office",1,[
        {t:"Pilot",d:"Michael Scott introduces his unique brand of management to an unsuspecting documentary film crew."},
        {t:"Diversity Day",d:"Michael conducts a wildly inappropriate and deeply cringeworthy diversity training session."},
        {t:"Health Care",d:"Michael puts Dwight in charge of selecting a health care plan with absolutely terrible results."},
        {t:"The Alliance",d:"Dwight forms a bizarre and completely unlikely secret alliance with his nemesis Jim."},
        {t:"Basketball",d:"Michael boldly challenges the warehouse staff to a heated and competitive game of basketball."},
        {t:"Hot Girl",d:"A saleswoman visits the office and immediately sparks intense competition among all the men."},
      ]),
      2:mk("office",2,[
        {t:"The Dundies",d:"Michael hosts his beloved annual Dundie Awards ceremony at a specific Chili's restaurant."},
        {t:"Sexual Harassment",d:"A corporate visit leads to an absolutely embarrassing office-wide sexual harassment seminar."},
        {t:"Office Olympics",d:"Jim organizes brilliantly hilarious office games while Michael is away buying a condo."},
        {t:"The Fire",d:"A mysterious fire alarm forces the entire Scranton office to wait outside together."},
        {t:"Halloween",d:"Michael must fire one person from the office on Halloween of all the possible days."},
      ]),
      3:mk("office",3,[
        {t:"Gay Witch Hunt",d:"Michael discovers that one of his employees is gay and handles the situation extremely poorly."},
        {t:"The Convention",d:"Michael and Dwight attend a paper supply convention held in Philadelphia together."},
        {t:"The Coup",d:"Dwight attempts to stage a quiet and stealthy coup against Michael's management position."},
        {t:"Grief Counseling",d:"Michael forces the entire office to participate in his unusual grief counseling session."},
      ]),
    }
  },
  { id:"brooklyn-99", title:"Brooklyn Nine-Nine", cat:"comedy", badge:"🚔", hue:"#1a5276", match:98, rating:"TV-14", yr:2013, sc:8,
    desc:"Hilarious ensemble comedy following the quirky detectives of the fictional 99th Precinct of the NYPD.",
    tags:["Comedy","Crime","Workplace"],
    s:{
      1:mk("b99",1,[
        {t:"Pilot",d:"Jake Peralta and the wonderfully quirky detectives of the 99th Precinct are introduced to viewers."},
        {t:"The Tagger",d:"Jake and new Captain Holt investigate a very specific serial vandalism case as a team."},
        {t:"The Slump",d:"The detectives struggle with a frustrating slump in their overall case-closing rate."},
        {t:"M.E. Time",d:"Jake's medical examiner friend helps the team work through a tricky murder case."},
        {t:"The Vulture",d:"A notorious case-stealing detective from another squad causes everyone serious headaches."},
      ]),
      2:mk("b99",2,[
        {t:"Undercover",d:"Jake returns from a months-long undercover assignment with some very big personal news."},
        {t:"Chocolate Milk",d:"Charles faces a sensitive medical procedure while trying to solve a difficult case."},
        {t:"The Jimmy Jab Games",d:"The detectives compete in ridiculously fun games while the captain is away."},
        {t:"Halloween II",d:"The beloved annual Halloween heist returns with even higher personal stakes than before."},
      ]),
    }
  },
  { id:"ted-lasso", title:"Ted Lasso", cat:"comedy", badge:"⚽", hue:"#1a6b3a", match:98, rating:"TV-MA", yr:2020, sc:3,
    desc:"An American college football coach is hired to manage an English soccer team despite having zero experience with the sport.",
    tags:["Comedy","Sports","Feel-Good"],
    s:{
      1:mk("ted",1,[
        {t:"Pilot",d:"Optimistic American coach Ted Lasso arrives in England completely unprepared for the Premier League."},
        {t:"Biscuits",d:"Ted tries to win over the skeptical owner of AFC Richmond with his special biscuits recipe."},
        {t:"Trent Crimm: The Independent",d:"A journalist shadows Ted to write a profile that could make or break his coaching career."},
        {t:"For the Children",d:"The team attends a charity auction and Ted makes a costly but heartfelt gesture."},
        {t:"Tan Lines",d:"Ted makes a controversial lineup decision that deeply divides the locker room."},
        {t:"Two Aces",d:"The team gets a new star player while Ted works to resolve tensions between teammates."},
      ]),
      2:mk("ted",2,[
        {t:"Goodbye Earl",d:"A team member departs while AFC Richmond struggles with the aftermath of a setback."},
        {t:"Lavender",d:"AFC Richmond's new sports psychologist faces a skeptical reception from the team."},
        {t:"Do the Right-est Thing",d:"Keeley invites a young woman to shadow her and Roy struggles with his television career."},
        {t:"Carol of the Bells",d:"Richmond holds a secret Santa event that brings the whole team unexpectedly together."},
      ]),
    }
  },
  { id:"parks-rec", title:"Parks and Recreation", cat:"comedy", badge:"🌳", hue:"#1a6b3a", match:97, rating:"TV-PG", yr:2009, sc:7,
    desc:"The absurd bureaucratic antics of an idealistic parks official in the wonderfully weird city of Pawnee, Indiana.",
    tags:["Comedy","Political","Mockumentary"],
    s:{
      1:mk("parks",1,[
        {t:"Pilot",d:"Enthusiastic Leslie Knope embarks on her ambitious mission to fill a pit with a beautiful park."},
        {t:"Canvassing",d:"Leslie determinedly polls local residents about her plan to convert the pit into a park."},
        {t:"The Reporter",d:"A local newspaper reporter comes to do a story on Leslie and the parks department."},
        {t:"Boys' Club",d:"Leslie slowly realizes she is not included in the male-dominated boys' club at work."},
        {t:"The Banquet",d:"Leslie receives a prestigious award and brings her difficult mother to the ceremony."},
        {t:"Rock Show",d:"Ann's musician boyfriend invites the entire parks department to his big rock show."},
      ]),
      2:mk("parks",2,[
        {t:"Pawnee Zoo",d:"Leslie officiates a penguin wedding at the zoo and faces a completely unexpected controversy."},
        {t:"The Stakeout",d:"Leslie and Ben go on an exhausting all-night stakeout together."},
        {t:"Beauty Pageant",d:"Leslie judges the local beauty pageant and makes some very surprising and bold decisions."},
        {t:"Practice Date",d:"Ann and Chris go on a carefully planned practice date to test their compatibility."},
      ]),
    }
  },
];

const CATS = [
  { id:"trending", label:"🔥 Trending Now" },
  { id:"kids",     label:"🧒 Kids" },
  { id:"action",   label:"⚡ Action & Thriller" },
  { id:"drama",    label:"🎭 Drama" },
  { id:"comedy",   label:"😂 Comedy" },
];
const catShows = cat =>
  cat === "trending"
    ? [...SHOWS].sort((a,b) => b.match - a.match).slice(0,12)
    : SHOWS.filter(s => s.cat === cat);

const HERO = SHOWS.find(s => s.id === "stranger-things");

// ════════════════════════════════════════════════════
// MICRO COMPONENTS
// ════════════════════════════════════════════════════
function Spinner() {
  return <div style={{width:16,height:16,borderRadius:"50%",border:"2px solid #ddd",borderTopColor:C.accent,animation:"spin 0.7s linear infinite",flexShrink:0}} />;
}
function GoogleG() {
  return (
    <svg width="18" height="18" viewBox="0 0 24 24">
      <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4"/>
      <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853"/>
      <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05"/>
      <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335"/>
    </svg>
  );
}
function InlineInput({ placeholder, type="text" }) {
  const [foc,setFoc] = useState(false);
  return (
    <input
      type={type} placeholder={placeholder}
      onFocus={()=>setFoc(true)} onBlur={()=>setFoc(false)}
      style={{width:"100%",padding:"12px 16px",background:"rgba(255,255,255,0.04)",border:`1px solid ${foc?C.borderBright:C.border}`,borderRadius:10,color:C.sub,fontSize:14,outline:"none",fontFamily:F,boxSizing:"border-box",transition:"border-color .15s"}}
    />
  );
}

// ════════════════════════════════════════════════════
// LOGIN — real Google OAuth
// ════════════════════════════════════════════════════
function Login({ onLogin }) {
  const [busy,setBusy] = useState(false);
  const [err,setErr]   = useState("");
  const [h1,setH1]     = useState(false);
  const [h2,setH2]     = useState(false);

  const googleLogin = useGoogleLogin({
    onSuccess: async (token) => {
      setBusy(true);
      setErr("");
      try {
        const res  = await fetch("https://www.googleapis.com/oauth2/v3/userinfo", {
          headers: { Authorization: `Bearer ${token.access_token}` },
        });
        const info = await res.json();
        onLogin({ name: info.name, email: info.email, picture: info.picture });
      } catch {
        setErr("Failed to get profile. Please try again.");
        setBusy(false);
      }
    },
    onError: () => {
      setErr("Google sign-in was cancelled or failed.");
      setBusy(false);
    },
  });

  return (
    <div style={{minHeight:"100vh",background:C.bg,display:"flex",alignItems:"center",justifyContent:"center",flexDirection:"column",fontFamily:F,position:"relative",overflow:"hidden"}}>
      <div style={{position:"absolute",width:700,height:700,borderRadius:"50%",background:"radial-gradient(circle, rgba(229,9,20,0.07) 0%, transparent 70%)",top:"50%",left:"50%",transform:"translate(-50%,-50%)",pointerEvents:"none"}} />
      <div style={{position:"absolute",inset:0,backgroundImage:"radial-gradient(rgba(255,255,255,0.022) 1px, transparent 1px)",backgroundSize:"28px 28px",pointerEvents:"none"}} />

      <div style={{textAlign:"center",marginBottom:44,zIndex:1}}>
        <div style={{fontFamily:D,fontSize:54,letterSpacing:4,background:"linear-gradient(135deg,#e50914 0%,#ff6b6b 100%)",WebkitBackgroundClip:"text",WebkitTextFillColor:"transparent",lineHeight:1}}>STREAMVERSE</div>
        <div style={{color:C.muted,fontSize:11,letterSpacing:5,marginTop:6,textTransform:"uppercase"}}>Every Show · Endless Worlds</div>
      </div>

      <div style={{background:"rgba(12,12,30,0.97)",border:`1px solid ${C.border}`,borderRadius:20,padding:"44px 48px",width:400,zIndex:1,boxShadow:"0 32px 80px rgba(0,0,0,0.75)"}}>
        <h2 style={{color:C.text,fontSize:22,fontWeight:700,margin:"0 0 6px",fontFamily:F}}>Welcome back</h2>
        <p style={{color:C.sub,fontSize:14,margin:"0 0 30px",fontFamily:F}}>Sign in to access your StreamVerse account</p>

        <button
          onMouseEnter={()=>setH1(true)} onMouseLeave={()=>setH1(false)}
          onClick={()=>{ setErr(""); googleLogin(); }}
          disabled={busy}
          style={{width:"100%",padding:"13px 20px",background:busy?"#e0e0e0":h1?"#f0f0f0":"#ffffff",border:"none",borderRadius:12,cursor:busy?"wait":"pointer",display:"flex",alignItems:"center",justifyContent:"center",gap:10,fontSize:15,fontWeight:600,color:"#1f1f1f",transition:"all .15s",transform:h1&&!busy?"translateY(-1px)":"none",boxShadow:h1?"0 8px 24px rgba(0,0,0,0.28)":"0 2px 8px rgba(0,0,0,0.14)",fontFamily:F}}
        >
          {busy ? <><Spinner/> Signing in...</> : <><GoogleG/> Continue with Google</>}
        </button>

        {err && (
          <div style={{marginTop:14,padding:"10px 14px",background:"rgba(229,9,20,0.1)",border:"1px solid rgba(229,9,20,0.3)",borderRadius:8,color:"#ff6b6b",fontSize:13,fontFamily:F}}>
            {err}
          </div>
        )}

        <div style={{display:"flex",alignItems:"center",gap:10,margin:"22px 0"}}>
          <div style={{flex:1,height:1,background:C.border}} />
          <span style={{color:C.muted,fontSize:12,fontFamily:F}}>or sign in with email</span>
          <div style={{flex:1,height:1,background:C.border}} />
        </div>

        <div style={{marginBottom:10}}><InlineInput placeholder="Email address" /></div>
        <div style={{marginBottom:20}}><InlineInput placeholder="Password" type="password" /></div>

        <button
          onMouseEnter={()=>setH2(true)} onMouseLeave={()=>setH2(false)}
          onClick={()=>{ setErr(""); googleLogin(); }}
          style={{width:"100%",padding:"14px",background:h2?"linear-gradient(135deg,#ff1a24,#c4080e)":"linear-gradient(135deg,#e50914,#c4080e)",border:"none",borderRadius:10,color:"#fff",fontSize:15,fontWeight:700,cursor:"pointer",fontFamily:F,transition:"all .15s",boxShadow:h2?`0 8px 24px ${C.accentGlow}`:"none",transform:h2?"translateY(-1px)":"none"}}
        >
          Sign In
        </button>

        <p style={{color:C.muted,fontSize:13,textAlign:"center",marginTop:20,fontFamily:F}}>
          No account?{" "}<span style={{color:C.accent,cursor:"pointer",fontWeight:600}}>Sign up free</span>
        </p>
      </div>

      <p style={{color:"#252540",fontSize:11,marginTop:22,zIndex:1,fontFamily:F}}>By continuing you agree to our Terms of Service & Privacy Policy</p>
      <style>{`@import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Outfit:wght@300;400;500;600;700;800;900&display=swap');@keyframes spin{to{transform:rotate(360deg)}}`}</style>
    </div>
  );
}

// ════════════════════════════════════════════════════
// NAVBAR
// ════════════════════════════════════════════════════
function Navbar({ user, onHome, onLogout }) {
  const [drop,setDrop] = useState(false);
  const navLinks = ["Home","TV Shows","Movies","New & Popular","My List"];
  return (
    <div style={{position:"fixed",top:0,left:0,right:0,zIndex:100,height:68,padding:"0 60px",display:"flex",alignItems:"center",justifyContent:"space-between",background:"linear-gradient(to bottom, rgba(5,5,14,0.97), rgba(5,5,14,0))",backdropFilter:"blur(10px)"}}>
      <button onClick={onHome} style={{background:"none",border:"none",cursor:"pointer",padding:0}}>
        <span style={{fontFamily:D,fontSize:30,letterSpacing:2,color:C.accent}}>STREAMVERSE</span>
      </button>
      <div style={{display:"flex",gap:26,alignItems:"center"}}>
        {navLinks.map(l => {
          const [hov,setHov] = useState(false);
          return <span key={l} onMouseEnter={()=>setHov(true)} onMouseLeave={()=>setHov(false)} style={{color:hov?C.text:C.sub,fontSize:14,cursor:"pointer",fontFamily:F,transition:"color .15s"}}>{l}</span>;
        })}
      </div>
      <div style={{display:"flex",alignItems:"center",gap:18,position:"relative"}}>
        <Search size={19} color={C.sub} style={{cursor:"pointer"}} />
        <Bell size={19} color={C.sub} style={{cursor:"pointer"}} />
        <div onClick={()=>setDrop(!drop)} style={{width:36,height:36,borderRadius:"50%",overflow:"hidden",cursor:"pointer",border:"2px solid rgba(255,255,255,0.15)",flexShrink:0}}>
          {user?.picture
            ? <img src={user.picture} alt={user.name} style={{width:"100%",height:"100%",objectFit:"cover"}} />
            : <div style={{width:"100%",height:"100%",background:"linear-gradient(135deg,#e50914,#8a0008)",display:"flex",alignItems:"center",justifyContent:"center",fontSize:14,fontWeight:700,color:"#fff",fontFamily:F}}>{user?.name?.[0]||"G"}</div>
          }
        </div>
        {drop && (
          <div style={{position:"absolute",top:"calc(100% + 10px)",right:0,background:C.surf,border:`1px solid ${C.borderBright}`,borderRadius:12,padding:"8px 0",minWidth:200,boxShadow:"0 20px 40px rgba(0,0,0,0.7)",zIndex:200}}>
            <div style={{padding:"12px 16px",borderBottom:`1px solid ${C.border}`,display:"flex",alignItems:"center",gap:10}}>
              {user?.picture && <img src={user.picture} alt="" style={{width:32,height:32,borderRadius:"50%",objectFit:"cover"}} />}
              <div>
                <div style={{color:C.text,fontSize:14,fontWeight:600,fontFamily:F}}>{user?.name}</div>
                <div style={{color:C.muted,fontSize:12,marginTop:1,fontFamily:F}}>{user?.email}</div>
              </div>
            </div>
            <div onClick={()=>{setDrop(false);onLogout();}} style={{padding:"10px 16px",display:"flex",alignItems:"center",gap:8,cursor:"pointer",color:C.sub,fontSize:14,fontFamily:F}} onMouseEnter={e=>e.currentTarget.style.color=C.text} onMouseLeave={e=>e.currentTarget.style.color=C.sub}>
              <LogOut size={14}/> Sign Out
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

// ════════════════════════════════════════════════════
// SHOW CARD
// ════════════════════════════════════════════════════
function ShowCard({ show, onClick }) {
  const [hov,setHov] = useState(false);
  return (
    <div onClick={()=>onClick(show)} onMouseEnter={()=>setHov(true)} onMouseLeave={()=>setHov(false)}
      style={{minWidth:155,maxWidth:155,cursor:"pointer",transition:"transform .22s ease, filter .22s ease",transform:hov?"scale(1.08)":"scale(1)",filter:hov?"brightness(1.1)":"brightness(1)",flexShrink:0}}>
      <div style={{width:155,height:232,borderRadius:10,overflow:"hidden",position:"relative",boxShadow:hov?"0 18px 44px rgba(0,0,0,0.7)":"0 4px 16px rgba(0,0,0,0.35)",transition:"box-shadow .22s"}}>
        <img src={`https://picsum.photos/seed/${show.id}/310/464`} alt={show.title} style={{width:"100%",height:"100%",objectFit:"cover",display:"block"}} onError={e=>{e.target.style.display="none";e.target.parentElement.style.background=show.hue;}} />
        <div style={{position:"absolute",inset:0,background:`linear-gradient(to bottom, transparent 45%, ${show.hue}77 100%)`}} />
        <div style={{position:"absolute",top:8,left:8,width:30,height:30,borderRadius:"50%",background:"rgba(0,0,0,0.6)",backdropFilter:"blur(4px)",display:"flex",alignItems:"center",justifyContent:"center",fontSize:14}}>{show.badge}</div>
        <div style={{position:"absolute",top:8,right:8,padding:"3px 7px",background:"rgba(0,0,0,0.65)",borderRadius:4,fontSize:10,color:C.sub,fontFamily:F,fontWeight:600}}>{show.rating}</div>
        {hov && <div style={{position:"absolute",inset:0,background:"rgba(0,0,0,0.38)",display:"flex",alignItems:"center",justifyContent:"center"}}><div style={{width:44,height:44,borderRadius:"50%",background:"rgba(255,255,255,0.93)",display:"flex",alignItems:"center",justifyContent:"center"}}><Play size={16} color="#111" fill="#111" style={{marginLeft:3}} /></div></div>}
        <div style={{position:"absolute",bottom:8,left:8,fontSize:11,color:C.green,fontFamily:F,fontWeight:700}}>{show.match}% Match</div>
      </div>
      <p style={{color:C.text,fontSize:13,fontWeight:600,marginTop:8,fontFamily:F,whiteSpace:"nowrap",overflow:"hidden",textOverflow:"ellipsis"}}>{show.title}</p>
      <p style={{color:C.muted,fontSize:11,marginTop:2,fontFamily:F}}>{show.yr} · {show.tags[0]}</p>
    </div>
  );
}

// ════════════════════════════════════════════════════
// CATEGORY ROW
// ════════════════════════════════════════════════════
function CatRow({ cat, onSelect }) {
  const rowRef = useRef(null);
  const shows  = catShows(cat.id);
  const scroll = dir => rowRef.current?.scrollBy({left:dir*520,behavior:"smooth"});
  return (
    <div style={{marginBottom:44}}>
      <h2 style={{color:C.text,fontSize:20,fontWeight:700,marginBottom:16,padding:"0 60px",fontFamily:F,letterSpacing:-0.2}}>{cat.label}</h2>
      <div style={{position:"relative"}}>
        <button onClick={()=>scroll(-1)} style={{position:"absolute",left:0,top:0,bottom:16,width:52,background:"linear-gradient(to right, rgba(5,5,14,0.92), transparent)",border:"none",color:C.sub,cursor:"pointer",zIndex:10,display:"flex",alignItems:"center",justifyContent:"center"}}><ChevronLeft size={24}/></button>
        <div ref={rowRef} style={{display:"flex",gap:12,overflowX:"auto",padding:"8px 60px 16px",scrollbarWidth:"none",msOverflowStyle:"none"}}>
          {shows.map(s => <ShowCard key={s.id} show={s} onClick={onSelect} />)}
        </div>
        <button onClick={()=>scroll(1)} style={{position:"absolute",right:0,top:0,bottom:16,width:52,background:"linear-gradient(to left, rgba(5,5,14,0.92), transparent)",border:"none",color:C.sub,cursor:"pointer",zIndex:10,display:"flex",alignItems:"center",justifyContent:"center"}}><ChevronRight size={24}/></button>
      </div>
    </div>
  );
}

// ════════════════════════════════════════════════════
// HERO BANNER
// ════════════════════════════════════════════════════
function HeroBanner({ onSelect }) {
  const show   = HERO;
  const [hp,setHp] = useState(false);
  const [hi,setHi] = useState(false);
  return (
    <div style={{position:"relative",height:550,overflow:"hidden"}}>
      <img src={`https://picsum.photos/seed/${show.id}-hero/1400/550`} alt="" style={{position:"absolute",inset:0,width:"100%",height:"100%",objectFit:"cover",objectPosition:"center 30%"}} />
      <div style={{position:"absolute",inset:0,background:"linear-gradient(to right, rgba(5,5,14,0.97) 30%, rgba(5,5,14,0.55) 65%, transparent 100%)"}} />
      <div style={{position:"absolute",inset:0,background:"linear-gradient(to top, rgba(5,5,14,1) 0%, transparent 55%)"}} />
      <div style={{position:"relative",padding:"0 60px",display:"flex",flexDirection:"column",justifyContent:"flex-end",height:"100%",paddingBottom:56}}>
        <div style={{display:"flex",gap:8,marginBottom:14}}>
          {show.tags.map(t=><span key={t} style={{padding:"4px 10px",background:"rgba(255,255,255,0.07)",border:`1px solid ${C.border}`,borderRadius:20,fontSize:12,color:C.sub,fontFamily:F}}>{t}</span>)}
        </div>
        <h1 style={{fontFamily:D,fontSize:74,color:C.text,letterSpacing:2,lineHeight:0.95,marginBottom:16,textShadow:"0 2px 24px rgba(0,0,0,0.5)"}}>{show.title}</h1>
        <div style={{display:"flex",alignItems:"center",gap:16,marginBottom:14}}>
          <span style={{color:C.green,fontWeight:700,fontSize:14,fontFamily:F}}>{show.match}% Match</span>
          <span style={{color:C.sub,fontSize:14,fontFamily:F}}>{show.yr}</span>
          <span style={{border:`1px solid ${C.sub}`,borderRadius:4,padding:"2px 6px",color:C.sub,fontSize:12,fontFamily:F}}>{show.rating}</span>
          <span style={{color:C.sub,fontSize:14,fontFamily:F}}>{show.sc} Seasons</span>
        </div>
        <p style={{color:"#c0c0d8",fontSize:15,maxWidth:460,lineHeight:1.6,marginBottom:28,fontFamily:F}}>{show.desc}</p>
        <div style={{display:"flex",gap:12}}>
          <button onMouseEnter={()=>setHp(true)} onMouseLeave={()=>setHp(false)} onClick={()=>onSelect(show)} style={{padding:"12px 28px",background:hp?"#e8e8e8":"#fff",border:"none",borderRadius:8,color:"#111",fontWeight:700,fontSize:15,cursor:"pointer",display:"flex",alignItems:"center",gap:8,fontFamily:F,transition:"all .15s"}}><Play size={18} fill="#111"/>Play</button>
          <button onMouseEnter={()=>setHi(true)} onMouseLeave={()=>setHi(false)} onClick={()=>onSelect(show)} style={{padding:"12px 28px",background:hi?"rgba(255,255,255,0.22)":"rgba(255,255,255,0.12)",border:"none",borderRadius:8,color:C.text,fontWeight:700,fontSize:15,cursor:"pointer",display:"flex",alignItems:"center",gap:8,fontFamily:F,transition:"all .15s"}}><Info size={18}/>More Info</button>
        </div>
      </div>
    </div>
  );
}

// ════════════════════════════════════════════════════
// HOME SCREEN
// ════════════════════════════════════════════════════
function Home({ onSelect, user, onLogout }) {
  return (
    <div style={{minHeight:"100vh",background:C.bg,fontFamily:F}}>
      <Navbar user={user} onHome={()=>{}} onLogout={onLogout} />
      <div style={{paddingTop:68}}>
        <HeroBanner onSelect={onSelect} />
        <div style={{paddingTop:40}}>
          {CATS.map(cat=><CatRow key={cat.id} cat={cat} onSelect={onSelect}/>)}
        </div>
        <div style={{height:60}} />
      </div>
      <style>{`@import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Outfit:wght@300;400;500;600;700;800;900&display=swap');*{box-sizing:border-box}div::-webkit-scrollbar{height:0}`}</style>
    </div>
  );
}

// ════════════════════════════════════════════════════
// EPISODE CARD
// ════════════════════════════════════════════════════
function EpCard({ ep, showId, sn }) {
  const [hov,setHov] = useState(false);
  return (
    <div onMouseEnter={()=>setHov(true)} onMouseLeave={()=>setHov(false)}
      style={{display:"flex",gap:18,background:hov?C.cardHov:C.card,border:`1px solid ${hov?C.borderBright:C.border}`,borderRadius:14,padding:18,cursor:"pointer",transition:"all .18s",marginBottom:14}}>
      <div style={{width:162,height:162,borderRadius:10,overflow:"hidden",flexShrink:0,position:"relative"}}>
        <img src={`https://picsum.photos/seed/${showId}-${sn}-${ep.n}/324/324`} alt={ep.title} style={{width:"100%",height:"100%",objectFit:"cover",display:"block"}} />
        {hov && <div style={{position:"absolute",inset:0,background:"rgba(0,0,0,0.45)",display:"flex",alignItems:"center",justifyContent:"center"}}><div style={{width:46,height:46,borderRadius:"50%",background:"rgba(255,255,255,0.9)",display:"flex",alignItems:"center",justifyContent:"center"}}><Play size={18} color="#111" fill="#111" style={{marginLeft:3}}/></div></div>}
        <div style={{position:"absolute",bottom:8,left:8,padding:"3px 8px",background:"rgba(0,0,0,0.72)",borderRadius:6,fontSize:11,color:"rgba(255,255,255,0.85)",fontFamily:F,fontWeight:700}}>EP {ep.n}</div>
      </div>
      <div style={{flex:1,display:"flex",flexDirection:"column",justifyContent:"center"}}>
        <div style={{color:C.muted,fontSize:11,fontFamily:F,fontWeight:700,textTransform:"uppercase",letterSpacing:1.5,marginBottom:7}}>Episode {ep.n}</div>
        <h3 style={{color:C.text,fontSize:16,fontWeight:700,fontFamily:F,marginBottom:9,lineHeight:1.3}}>{ep.title}</h3>
        <p style={{color:C.sub,fontSize:13,fontFamily:F,lineHeight:1.6,marginBottom:12}}>{ep.desc}</p>
        <div style={{display:"flex",alignItems:"center",gap:12}}>
          <span style={{color:C.muted,fontSize:12,fontFamily:F}}>⏱ {ep.dur}</span>
          <span style={{padding:"3px 10px",background:"rgba(70,211,105,0.1)",border:"1px solid rgba(70,211,105,0.2)",borderRadius:20,fontSize:11,color:C.green,fontFamily:F,fontWeight:600}}>HD</span>
        </div>
      </div>
    </div>
  );
}

// ════════════════════════════════════════════════════
// SHOW SCREEN
// ════════════════════════════════════════════════════
function ShowScreen({ show, season, onSeason, onBack, user }) {
  const eps     = getEps(show, season);
  const seasons = Array.from({length:show.sc},(_,i)=>i+1);
  const [hb,setHb] = useState(false);
  return (
    <div style={{minHeight:"100vh",background:C.bg,fontFamily:F}}>
      <div style={{position:"fixed",top:0,left:0,right:0,zIndex:100,height:68,padding:"0 60px",display:"flex",alignItems:"center",justifyContent:"space-between",background:"rgba(5,5,14,0.97)",backdropFilter:"blur(12px)",borderBottom:`1px solid ${C.border}`}}>
        <div style={{display:"flex",alignItems:"center",gap:18}}>
          <button onMouseEnter={()=>setHb(true)} onMouseLeave={()=>setHb(false)} onClick={onBack} style={{background:hb?"rgba(255,255,255,0.1)":"rgba(255,255,255,0.05)",border:`1px solid ${C.border}`,borderRadius:8,padding:"8px 14px",color:C.text,cursor:"pointer",display:"flex",alignItems:"center",gap:6,fontSize:14,fontFamily:F,fontWeight:600,transition:"all .15s"}}>
            <ChevronLeft size={16}/> Back
          </button>
          <span style={{fontFamily:D,fontSize:28,letterSpacing:2,color:C.accent}}>STREAMVERSE</span>
        </div>
        <div style={{width:36,height:36,borderRadius:"50%",overflow:"hidden",border:"2px solid rgba(255,255,255,0.15)"}}>
          {user?.picture
            ? <img src={user.picture} alt="" style={{width:"100%",height:"100%",objectFit:"cover"}} />
            : <div style={{width:"100%",height:"100%",background:"linear-gradient(135deg,#e50914,#8a0008)",display:"flex",alignItems:"center",justifyContent:"center",fontSize:14,fontWeight:700,color:"#fff",fontFamily:F}}>{user?.name?.[0]||"G"}</div>
          }
        </div>
      </div>

      <div style={{paddingTop:68}}>
        <div style={{position:"relative",height:400,overflow:"hidden"}}>
          <img src={`https://picsum.photos/seed/${show.id}-banner/1400/400`} alt="" style={{position:"absolute",inset:0,width:"100%",height:"100%",objectFit:"cover",objectPosition:"center 25%"}} />
          <div style={{position:"absolute",inset:0,background:"linear-gradient(to right, rgba(5,5,14,0.95) 35%, transparent 75%)"}} />
          <div style={{position:"absolute",inset:0,background:"linear-gradient(to top, rgba(5,5,14,1) 0%, transparent 60%)"}} />
          <div style={{position:"absolute",bottom:0,left:0,padding:"0 60px 36px"}}>
            <div style={{width:54,height:54,borderRadius:"50%",background:`${show.hue}cc`,border:"2px solid rgba(255,255,255,0.18)",display:"flex",alignItems:"center",justifyContent:"center",fontSize:24,marginBottom:12}}>{show.badge}</div>
            <h1 style={{fontFamily:D,fontSize:60,color:C.text,letterSpacing:1.5,lineHeight:1,marginBottom:12}}>{show.title}</h1>
            <div style={{display:"flex",alignItems:"center",gap:16,marginBottom:12}}>
              <span style={{color:C.green,fontWeight:700,fontSize:14,fontFamily:F}}>{show.match}% Match</span>
              <span style={{color:C.sub,fontSize:14,fontFamily:F}}>{show.yr}</span>
              <span style={{border:`1px solid ${C.sub}`,borderRadius:4,padding:"2px 6px",color:C.sub,fontSize:12,fontFamily:F}}>{show.rating}</span>
              <span style={{color:C.sub,fontSize:14,fontFamily:F}}>{show.sc} Seasons</span>
            </div>
            <p style={{color:"#b8b8cc",fontSize:14,maxWidth:500,lineHeight:1.6,fontFamily:F,marginBottom:16}}>{show.desc}</p>
            <div style={{display:"flex",gap:8}}>
              {show.tags.map(t=><span key={t} style={{padding:"4px 12px",background:"rgba(255,255,255,0.06)",border:`1px solid ${C.border}`,borderRadius:20,fontSize:12,color:C.sub,fontFamily:F}}>{t}</span>)}
            </div>
          </div>
        </div>

        <div style={{display:"flex",padding:"0 60px",gap:40,paddingBottom:80}}>
          {/* season sidebar */}
          <div style={{width:200,flexShrink:0,paddingTop:40}}>
            <p style={{color:C.muted,fontSize:11,fontWeight:700,textTransform:"uppercase",letterSpacing:2,marginBottom:16,fontFamily:F}}>Seasons</p>
            <div style={{maxHeight:420,overflowY:"auto",scrollbarWidth:"thin",scrollbarColor:`${C.muted} transparent`}}>
              {seasons.map(sn => {
                const active = season === sn;
                const [sh,setSh] = useState(false);
                return (
                  <button key={sn} onClick={()=>onSeason(sn)} onMouseEnter={()=>setSh(true)} onMouseLeave={()=>setSh(false)}
                    style={{width:"100%",padding:"12px 16px",background:active?C.accent:sh?"rgba(255,255,255,0.05)":"transparent",border:`1px solid ${active?C.accent:sh?C.borderBright:C.border}`,borderRadius:10,color:active?"#fff":sh?C.text:C.sub,textAlign:"left",cursor:"pointer",marginBottom:8,fontSize:14,fontFamily:F,fontWeight:active?700:400,display:"flex",alignItems:"center",gap:10,transition:"all .15s",boxShadow:active?`0 4px 16px ${C.accentGlow}`:"none"}}>
                    {active ? <Check size={14}/> : <span style={{width:14}}/>}
                    Season {sn}
                  </button>
                );
              })}
            </div>
          </div>

          {/* episode list */}
          <div style={{flex:1,paddingTop:40}}>
            <div style={{display:"flex",alignItems:"center",justifyContent:"space-between",marginBottom:22}}>
              <div>
                <h3 style={{color:C.text,fontSize:22,fontWeight:700,fontFamily:F,margin:0}}>Season {season}</h3>
                <p style={{color:C.muted,fontSize:13,fontFamily:F,marginTop:4}}>{eps.length} Episodes</p>
              </div>
              <div style={{padding:"6px 14px",background:"rgba(229,9,20,0.12)",border:`1px solid ${C.accent}44`,borderRadius:20,fontSize:12,color:C.accent,fontFamily:F,fontWeight:600}}>
                Season 1 by default
              </div>
            </div>
            {eps.map(ep=><EpCard key={ep.n} ep={ep} showId={show.id} sn={season}/>)}
          </div>
        </div>
      </div>
      <style>{`@import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Outfit:wght@300;400;500;600;700;800;900&display=swap');*{box-sizing:border-box}`}</style>
    </div>
  );
}

// ════════════════════════════════════════════════════
// ROOT APP
// ════════════════════════════════════════════════════
export default function App() {
  const [screen, setScreen] = useState("login");
  const [user,   setUser]   = useState(null);
  const [show,   setShow]   = useState(null);
  const [season, setSeason] = useState(1);

  const login    = u => { setUser(u); setScreen("home"); };
  const openShow = s => { setShow(s); setSeason(1); setScreen("show"); };
  const goHome   = ()  => { setScreen("home"); setShow(null); };
  const logout   = ()  => { setScreen("login"); setUser(null); };

  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID || ""}>
      {screen === "login" && <Login onLogin={login} />}
      {screen === "home"  && <Home  onSelect={openShow} user={user} onLogout={logout} />}
      {screen === "show"  && <ShowScreen show={show} season={season} onSeason={setSeason} onBack={goHome} user={user} />}
    </GoogleOAuthProvider>
  );
}
