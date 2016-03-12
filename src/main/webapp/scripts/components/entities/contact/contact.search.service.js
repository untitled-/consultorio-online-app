'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('ContactSearch', function ($resource) {
        return $resource('api/_search/contacts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
