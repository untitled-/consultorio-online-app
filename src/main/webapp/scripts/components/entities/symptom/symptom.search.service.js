'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('SymptomSearch', function ($resource) {
        return $resource('api/_search/symptoms/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
