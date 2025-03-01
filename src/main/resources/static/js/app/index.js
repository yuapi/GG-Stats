const main = {
    init: function () {
        const _this = this;

        tailwind.config = {
            darkMode: 'class',
            theme: {
                extend: {
                    colors: {
                        primary: '#FF9800',
                        darkBg: '#242424',
                        darkText: '#FFFFFF',
                        mutedText: '#B3B3B3',
                    },
                },
            },
        };

        $('#theme-toggle').on('click', function () {
            _this.toggleTheme();
        })
    },
    toggleTheme: function () {
        const lightIcon = $('#theme-toggle-light-icon');
        const darkIcon = $('#theme-toggle-dark-icon');
        const html = $('html').toggleClass('dark');

        if (html.hasClass('dark')) {
            lightIcon.addClass('hidden');
            darkIcon.removeClass('hidden');
        } else {
            lightIcon.removeClass('hidden');
            darkIcon.addClass(('hidden'));
        }
    }
}

main.init();