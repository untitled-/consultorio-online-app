'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('ImmunizationSearch', function ($resource) {
        return $resource('api/_search/immunizations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
