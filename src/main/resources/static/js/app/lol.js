const lol = {
    init: function () {
        const _this = this;
        $('#searchForm').on('submit', function (event) {
            event.preventDefault();
            _this.search();
        })
    },
    search: function () {
        const form = $('#searchForm');
        const data = {
            region: form.find('[name="region"]').val(),
            encodedName: encodeURIComponent(form.find('[name="summonerName"]').val().trim()),
        };

        window.location.href = `/lol/summoner/${data.region}/${data.encodedName}`;
    }
}

lol.init();