'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('ContactSearch', function ($resource) {
        return $resource('api/_search/contacts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
