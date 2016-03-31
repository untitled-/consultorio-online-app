'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('AlergySearch', function ($resource) {
        return $resource('api/_search/alergys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
