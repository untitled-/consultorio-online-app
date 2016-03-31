'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('ContraceptivesSearch', function ($resource) {
        return $resource('api/_search/contraceptivess/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
