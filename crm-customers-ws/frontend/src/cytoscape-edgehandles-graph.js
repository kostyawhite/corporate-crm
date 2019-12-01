import {PolymerElement,html} from '@polymer/polymer/polymer-element.js';
import cytoscape from 'cytoscape/dist/cytoscape.esm.min.js';
import edgehandles from 'cytoscape-edgehandles/cytoscape-edgehandles.js';

let cyMap = new Map();
cytoscape.use(edgehandles);

class CytoscapeEdgeHandlesGraph extends PolymerElement {
    static get template() {
        return html`
            <style>
                #cy {
                  width: 700px;
                  height: 400px;
                  display: block;
                }
            </style>
            <div id$="[[templateId]]" style="display: none;"></div>
            <div id="cy">
            </div>`
    }

    static get is() {return 'cytoscape-edgehandles-graph'}

    attached() {

        let cy = cytoscape({

          container: this.shadowRoot.querySelector("#cy"),

          elements: [

          ],

          style: [
            {
              selector: 'node',
              style: {
                'background-color': '#1676F3',
                'label': 'data(id)',
                'color': '#000',
                'font-size': '18px'
              }
            },


            {
              selector: 'edge',
              style: {
                'width': 3,
                'curve-style': 'bezier',
                'line-color': '#ссс',
                'target-arrow-color': '#666',
                'target-arrow-shape': 'triangle'
              }
            },

            {
                selector: '.eh-handle',
                style: {
                  'background-color': 'red',
                  'width': 12,
                  'height': 12,
                  'shape': 'ellipse',
                  'overlay-opacity': 0,
                  'border-width': 12, // makes the handle easier to hit
                  'border-opacity': 0
                }
              },

              {
                selector: '.eh-hover',
                style: {
                  'background-color': 'red'
                }
              },

              {
                selector: '.eh-source',
                style: {
                  'border-width': 2,
                  'border-color': 'red'
                }
              },

              {
                selector: '.eh-target',
                style: {
                  'border-width': 2,
                  'border-color': 'red'
                }
              },

              {
                selector: '.eh-preview, .eh-ghost-edge',
                style: {
                  'background-color': 'red',
                  'line-color': 'red',
                  'target-arrow-color': 'red',
                  'source-arrow-color': 'red'
                }
              },

              {
                selector: '.eh-ghost-edge.eh-preview-active',
                style: {
                  'opacity': 0
                }
              }
          ],

        });

        cy.userPanningEnabled(false);
        let eh = cy.edgehandles();
        eh.enableDrawMode();

        cy.on('ehcomplete', (event, sourceNode, targetNode, addedEles) => {
        	let { position } = event;
        	let data = addedEles.json().data;
        	if (cy.edges('[source = "' + data.source + '"][target = "' + data.target + '"]').length > 1) {
        	    cy.remove(addedEles);
        	}
        });

        return cy;
    }

    ready() {
        super.ready();
        cyMap.set(this.templateId, this.attached());
    }

    setData(nodes, edges) {
        var nodesArray = JSON.parse(nodes);
        var edgesArray = JSON.parse(edges);
        let cy = cyMap.get(this.templateId);
        for (var i = 0; i < nodesArray.nodes.length; i++) {
            cy.add({ group: 'nodes', data: { id: nodesArray.nodes[i].id }});
        }
        for (var i = 0; i < edgesArray.edges.length; i++) {
            cy.add({ group: 'edges', data: { source: edgesArray.edges[i].source, target: edgesArray.edges[i].target }});
        }

        cy.mount(this.shadowRoot.querySelector("#cy"));

        var layout = cy.layout({
            name: 'circle',
        });

        layout.run();
    }

    resetEdges() {
        let cy = cyMap.get(this.templateId);
        cy.remove(cy.edges());
    }

    resetNodes() {
        let cy = cyMap.get(this.templateId);
        cy.remove(cy.nodes());
    }

    getEdges() {
       let cy = cyMap.get(this.templateId);
       let array = []
       let els = cy.edges().jsons();

       for (var i = 0; i < els.length; ++i) {
           array.push('{"source":"' + els[i].data.source + '","target":"' + els[i].data.target + '"}');
       }

        return '{"edges":[' + array.join() + ']}';
    }

    getNodes() {
        let cy = cyMap.get(this.templateId);
        let array = []
        let els = cy.edges().jsons();

        let cySet = new Set();
        for (var i = 0; i < els.length; ++i) {
            cySet.add('{"id":"' + els[i].data.source + '"}');
            cySet.add('{"id":"' + els[i].data.target + '"}');
        }

        return '{"nodes":[' + Array.from(cySet).join() + ']}';
     }

    resetGraph() {
        cyMap.delete(this.templateId);
    }
}

customElements.define(CytoscapeEdgeHandlesGraph.is, CytoscapeEdgeHandlesGraph);