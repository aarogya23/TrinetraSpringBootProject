 const games = [
    { title: "Cyberpunk 2077", image: "/image/Cyberpunk.png", url: "cyberpunk.html" },
    { title: "Grand Theft Auto 5", image: "/image/gta5.png", url: "gta.html" },
    { title: "Red Dead Redemption 2", image: "rdr2.jpg", url: "rdr2.html" },
    { title: "God of War", image: "/image/Godofwar.jpg", url: "Godofwarbro.html" },
    { title: "Ea FC 24", image: "/image/Ea fc.png", url: "Godofwarbro.html" },
    { title: "Ea FC 25", image: "/image/eafc25.jpg", url: "Eafc 25.html" },
    { title: "Modern Warfare 3", image: "/image/horizon.jpg", url: "mw3.html" },
    { title: "Spider-Man: Miles Morales", image: "/image/spiderman.jpg", url: "spiderman.html" },
    { title: "Assassin's Creed Valhalla", image: "/image/acvalhalla.jpg", url: "acvalhalla.html" },
    { title: "Ghost of Tsushima", image: "tsushima.jpg", url: "tsushima.html" },
    { title: "Elden Ring", image: "eldenring.jpg", url: "eldenring.html" },
    { title: "Resident Evil Village", image: "residentevilvillage.jpg", url: "residentevilvillage.html" },
    { title: "Call of Duty: Modern Warfare", image: "codmw.jpg", url: "Product Detail.html" },
    { title: "Fortnite", image: "fortnite.jpg", url: "fortnite.html" },
    { title: "Apex Legends", image: "apexlegends.jpg", url: "apexlegends.html" },
    { title: "League of Legends", image: "lol.jpg", url: "lol.html" },
    { title: "Valorant", image: "valorant.jpg", url: "valorant.html" },
    { title: "Overwatch", image: "overwatch.jpg", url: "overwatch.html" },
    { title: "Battlefield V", image: "battlefieldv.jpg", url: "battlefieldv.html" },
    { title: "Far Cry 6", image: "far cry.png", url: "FARCRY.html" },
    { title: "Death Stranding", image: "deathstranding.jpg", url: "deathstranding.html" },
    { title: "Metro Exodus", image: "metroexodus.jpg", url: "metroexodus.html" },
    { title: "FIFA 23", image: "fifa21.jpg", url: "fifa23.html" },
    { title: "Fifa 18", image: "nba2k21.jpg", url: "fifa18.html" },
    { title: "FIFA 17", image: "madden21.jpg", url: "fifa17.html" },
    { title: "FIFA 14", image: "gears5.jpg", url: "fifa14.html" },
    { title: "PUBG PC", image: "halo.jpg", url: "pubg.html" },
    { title: "Hogwarts", image: "doom.jpg", url: "hogwarts.html" }
];

function searchGames() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const searchResults = document.getElementById('searchResults');
    searchResults.innerHTML = '';

    if (searchInput) {
        const filteredGames = games.filter(game => game.title.toLowerCase().includes(searchInput));
        if (filteredGames.length > 0) {
            searchResults.style.display = 'block';
            filteredGames.forEach(game => {
                const resultItem = document.createElement('div');
                resultItem.classList.add('search-result-item');

                // Create a link element
                const gameLink = document.createElement('a');
                gameLink.href = game.url;

                // Create the image element
                const gameImage = document.createElement('img');
                gameImage.src = game.image;
                gameImage.alt = game.title;

                // Create the title element
                const gameTitle = document.createElement('span');
                gameTitle.textContent = game.title;

                // Append image and title to the link
                gameLink.appendChild(gameImage);
                gameLink.appendChild(gameTitle);

                // Append the link to the result item
                resultItem.appendChild(gameLink);

                // Append the result item to the search results container
                searchResults.appendChild(resultItem);
            });
        } else {
            searchResults.style.display = 'none';
        }
    } else {
        searchResults.style.display = 'none';
    }
}