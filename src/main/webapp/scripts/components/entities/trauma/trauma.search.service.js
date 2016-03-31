'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('TraumaSearch', function ($resource) {
        return $resource('api/_search/traumas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
