'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('nonPathologicBkg', {
                parent: 'entity',
                url: '/nonPathologicBkgs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.nonPathologicBkg.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nonPathologicBkg/nonPathologicBkgs.html',
                        controller: 'NonPathologicBkgController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nonPathologicBkg');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('nonPathologicBkg.detail', {
                parent: 'entity',
                url: '/nonPathologicBkg/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.nonPathologicBkg.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/nonPathologicBkg/nonPathologicBkg-detail.html',
                        controller: 'NonPathologicBkgDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('nonPathologicBkg');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'NonPathologicBkg', function($stateParams, NonPathologicBkg) {
                        return NonPathologicBkg.get({id : $stateParams.id});
                    }]
                }
            })
            .state('nonPathologicBkg.new', {
                parent: 'nonPathologicBkg',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/nonPathologicBkg/nonPathologicBkg-dialog.html',
                        controller: 'NonPathologicBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    housing: null,
                                    hasZoonosis: null,
                                    zoonosisDesc: null,
                                    isOvercrowded: null,
                                    overcrowdingDesc: null,
                                    isFeedingBalanced: null,
                                    feedingDesc: null,
                                    hygieneDesc: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('nonPathologicBkg', null, { reload: true });
                    }, function() {
                        $state.go('nonPathologicBkg');
                    })
                }]
            })
            .state('nonPathologicBkg.edit', {
                parent: 'nonPathologicBkg',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/nonPathologicBkg/nonPathologicBkg-dialog.html',
                        controller: 'NonPathologicBkgDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NonPathologicBkg', function(NonPathologicBkg) {
                                return NonPathologicBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nonPathologicBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('nonPathologicBkg.delete', {
                parent: 'nonPathologicBkg',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/nonPathologicBkg/nonPathologicBkg-delete-dialog.html',
                        controller: 'NonPathologicBkgDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['NonPathologicBkg', function(NonPathologicBkg) {
                                return NonPathologicBkg.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('nonPathologicBkg', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
