'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('ImmunizationSearch', function ($resource) {
        return $resource('api/_search/immunizations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
