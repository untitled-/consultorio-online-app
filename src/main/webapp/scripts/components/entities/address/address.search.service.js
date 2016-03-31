'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('AddressSearch', function ($resource) {
        return $resource('api/_search/addresss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
