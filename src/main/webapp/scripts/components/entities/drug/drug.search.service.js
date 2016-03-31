'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('DrugSearch', function ($resource) {
        return $resource('api/_search/drugs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
