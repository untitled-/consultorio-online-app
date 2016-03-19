'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('TraumaSearch', function ($resource) {
        return $resource('api/_search/traumas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
